package world.cepi.luae.script

import kotlinx.serialization.Serializable
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import net.minestom.server.item.ItemStack
import net.minestom.server.item.Material
import net.minestom.server.tag.Tag
import org.graalvm.polyglot.Context
import org.graalvm.polyglot.Engine
import org.graalvm.polyglot.management.ExecutionEvent
import world.cepi.kstom.item.item
import world.cepi.kstom.item.withMeta
import org.graalvm.polyglot.management.ExecutionListener


/**
 * Something that can run code w/ context.
 */
@Serializable
class Script(val content: String = "") {

    companion object {
        const val key = "luae-script"
        const val currentLanguage = "js"
    }

    /**
     * Runs a set of objects with some context.
     *
     * @return If this succeeded or not.
     */
fun run(scriptContext: ScriptContext): RunResult {

        val oldCl = Thread.currentThread().contextClassLoader
        Thread.currentThread().contextClassLoader = javaClass.classLoader
        Context
            .newBuilder(currentLanguage)
            .build()
            .use { context ->

                val listener = ExecutionListener.newBuilder()
                    .onEnter { e: ExecutionEvent ->
                        // TODO stop running after X amount of iterations.
                    }
                    .statements(true)
                    .attach(context.engine)

                context.getBindings(currentLanguage).apply {
                    putMember("executor", Executor)
                    putMember("player", scriptContext.player)
                    putMember("position", scriptContext.position)
                    putMember("instance", scriptContext.instance)
                }

                try {
                    context.eval(currentLanguage, content)
                } catch (e: Exception) {
                    scriptContext.player?.sendMessage(
                        Component.text(e.message ?: "An internal error occured while running this script", NamedTextColor.RED)
                    )
                }
                listener.close()
        }

        Thread.currentThread().contextClassLoader = oldCl

        return RunResult.SUCCESS
}

    fun asItem(): ItemStack = item(Material.PAPER) {
        displayName(Component.text("Script", NamedTextColor.GREEN))

        withMeta {
            this.setTag(Tag.String(key), content)
        }
    }


}

val ItemStack.scriptString: String?
    get() = this.meta.getTag(Tag.String(Script.key))