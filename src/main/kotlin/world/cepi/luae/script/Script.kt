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
import world.cepi.luae.script.lib.ScriptContext
import world.cepi.luae.script.lib.ScriptPlayer
import world.cepi.luae.script.lib.ScriptPos
import world.cepi.luae.script.lib.ScriptVec

/**
 * Something that can run code w/ context.
 */
@Serializable
class Script(val content: String = "") {

    companion object {
        const val key = "luae-script"
    }

    /**
     * Runs a set of objects with some context.
     *
     * @return If this succeeded or not.
     */
    fun run(scriptContext: ScriptContext): RunResult {

        val oldCl = Thread.currentThread().contextClassLoader
        Thread.currentThread().contextClassLoader = javaClass.classLoader
		val time = System.currentTimeMillis();

        var count = 0

        Context.newBuilder("js")
            .allowExperimentalOptions(true)
            .allowHostAccess(ScriptableExplicitConfig.explicitMode)
            .build()
            .use { context ->

                context.getBindings("js").apply {
                    putMember("context", scriptContext)
                    putMember("Pos", ScriptPos)
                    putMember("Vec", ScriptVec)
                }

                val listener = ExecutionListener.newBuilder()
                    .onEnter { count++ }
                    .expressions(true)
                    .statements(true)
                    .roots(true)
                    .attach(context.engine)

                try {
                    val returnValue = context.eval("js", content)
                    scriptContext.player?.sendMessage(
                        Component.text(returnValue.toString(), NamedTextColor.GRAY)
                            .append(Component.text(" (${System.currentTimeMillis() - time}ms)", NamedTextColor.BLUE))
                            .append(Component.text(" [steps -> $count]", NamedTextColor.GOLD))
                    )
                } catch (e: PolyglotException) {
                    scriptContext.player?.sendMessage(Component.text("error: $e", NamedTextColor.RED))
                    return RunResult.Error(e.toString())
                }

                listener.close()
        }
        Thread.currentThread().contextClassLoader = oldCl

        return RunResult.Success
    }

    fun runAsPlayer(player: Player) {
        val runResult = run(
            ScriptContext(
            ScriptPlayer(player),
            ScriptPos.fromPosition(player.position)
        )
        )

        when (runResult) {
            is RunResult.Success -> {}
            is RunResult.Error -> {
                Component.text(runResult.message ?: "An internal error occurred while running this script", NamedTextColor.RED)
            }
        }
    }

    fun asItem(): ItemStack = item(Material.PAPER) {
        displayName(Component.text("Script", NamedTextColor.GREEN).decoration(TextDecoration.ITALIC, false))

        withMeta {
            this.setTag(Tag.String(key), content)
        }
    }


}

val ItemStack.scriptString: String?
    get() = this.meta.getTag(Tag.String(Script.key))