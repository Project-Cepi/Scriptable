package world.cepi.scriptable.script

import kotlinx.serialization.Serializable
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.text.format.TextDecoration
import net.minestom.server.entity.Entity
import net.minestom.server.entity.Player
import net.minestom.server.item.ItemStack
import net.minestom.server.item.Material
import net.minestom.server.tag.Tag
import org.graalvm.polyglot.Context
import org.graalvm.polyglot.PolyglotException
import org.graalvm.polyglot.management.ExecutionListener
import world.cepi.kstom.item.item
import world.cepi.kstom.item.withMeta
import world.cepi.scriptable.script.access.ScriptableExplicitConfig
import world.cepi.scriptable.script.lib.*
import java.time.Instant
import java.util.*
import java.util.concurrent.atomic.AtomicInteger
import kotlin.concurrent.thread

/**
 * Something that can run code w/ context.
 */
@Serializable
class Script(val content: String = "") {

    companion object {
        const val key = "luae-script"
    }

    fun graalContext() = Context.newBuilder("js")
        .allowExperimentalOptions(true)
        .allowHostAccess(ScriptableExplicitConfig.explicitMode)
        .build()

    /**
     * Runs a set of objects with some context.
     *
     * @return If this succeeded or not.
     */
    fun run(scriptContext: ScriptContext) {

        val oldCl = Thread.currentThread().contextClassLoader
        Thread.currentThread().contextClassLoader = javaClass.classLoader
		val time = System.currentTimeMillis();

        val atomicInteger = AtomicInteger()
        val uuid = UUID.randomUUID()

        val currentThread = thread {
            graalContext().use { context ->
                context.getBindings("js").apply {
                    putMember("context", scriptContext)
                    putMember("Pos", ScriptPos)
                    putMember("Vec", ScriptVec)
                    putMember("Audiences", ScriptAudiences)
                    putMember("Chance", ScriptChance) // Chance.chance
                    putMember("Timer", ScriptTimer) // Timer.sleep
                    putMember("Executor", ScriptExecutor(scriptContext)) // Executor.execute
                }

                val listener = ExecutionListener.newBuilder()
                    .onEnter {
                        val currentAmount = atomicInteger.addAndGet(1)

                        if (currentAmount > 10_000) {
                            scriptContext.player?.player?.sendMessage(
                                Component.text("Script over 10,000 steps. Stopping", NamedTextColor.RED)
                            )
                            ScriptManager.interrupt(uuid)
                        }
                    }
                    .expressions(true)
                    .statements(true)
                    .roots(true)
                    .attach(context.engine)

                try {
                    val returnValue = context.eval("js", content)
                    scriptContext.player?.player?.sendMessage(
                        Component.text(returnValue.toString(), NamedTextColor.GRAY)
                            .append(Component.text(" (${System.currentTimeMillis() - time}ms)", NamedTextColor.BLUE))
                            .append(Component.text(" [steps -> ${atomicInteger.get()}]", NamedTextColor.GOLD))
                    )
                } catch (e: PolyglotException) {
                    scriptContext.player?.player?.sendMessage(Component.text("error: ${e.message}", NamedTextColor.RED))
                }

                listener.close()
                ScriptManager.runningScripts.remove(uuid)
            }
        }

        val result = ScriptResult(Instant.now(), atomicInteger, currentThread)

        Thread.currentThread().contextClassLoader = oldCl

        ScriptManager.runningScripts[uuid] = result
    }

    fun runAsPlayer(player: Player) = run(ScriptContext(
        ScriptPlayer(player),
        ScriptEntity(player),
        ScriptPos.fromPosition(player.position)
    ))

    fun runAsEntity(entity: Entity) = run(ScriptContext(
        null,
        ScriptEntity(entity),
        ScriptPos.fromPosition(entity.position)
    ))


    fun asItem(): ItemStack = item(Material.PAPER) {
        displayName(Component.text("Script", NamedTextColor.GREEN).decoration(TextDecoration.ITALIC, false))

        withMeta {
            this.setTag(Tag.String(key), content)
        }
    }


}

val ItemStack.scriptString: String?
    get() = this.meta.getTag(Tag.String(Script.key))