package world.cepi.luae.script

import kotlinx.serialization.Serializable
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.text.format.TextDecoration
import net.minestom.server.entity.Player
import net.minestom.server.item.ItemStack
import net.minestom.server.item.Material
import net.minestom.server.tag.Tag
import org.graalvm.polyglot.Context
import org.graalvm.polyglot.PolyglotException
import org.graalvm.polyglot.management.ExecutionListener
import world.cepi.kstom.item.item
import world.cepi.kstom.item.withMeta
import world.cepi.luae.script.access.ScriptableExplicitConfig
import world.cepi.luae.script.lib.*
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

        var count = 0

        thread {
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
                    .onEnter { count++ }
                    .expressions(true)
                    .statements(true)
                    .roots(true)
                    .attach(context.engine)

                try {
                    val returnValue = context.eval("js", content)
                    scriptContext.player?.player?.sendMessage(
                        Component.text(returnValue.toString(), NamedTextColor.GRAY)
                            .append(Component.text(" (${System.currentTimeMillis() - time}ms)", NamedTextColor.BLUE))
                            .append(Component.text(" [steps -> $count]", NamedTextColor.GOLD))
                    )
                } catch (e: PolyglotException) {
                    scriptContext.player?.player?.sendMessage(Component.text("error: ${e.message}", NamedTextColor.RED))
                }

                listener.close()
            }
        }

        Thread.currentThread().contextClassLoader = oldCl
    }

    fun runAsPlayer(player: Player) = run(ScriptContext(
        ScriptPlayer(player),
        ScriptPos.fromPosition(player.position)
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