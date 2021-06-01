package world.cepi.luae.script

import kotlinx.serialization.Serializable
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import net.minestom.server.item.ItemStack
import net.minestom.server.item.Material
import net.minestom.server.tag.Tag
import org.graalvm.polyglot.Context
import org.graalvm.polyglot.management.ExecutionEvent
import world.cepi.kstom.item.clientData
import world.cepi.kstom.item.get
import world.cepi.kstom.item.item
import world.cepi.kstom.item.withMeta
import world.cepi.luae.script.Script.Companion.key
import org.graalvm.polyglot.management.ExecutionListener
import world.cepi.kstom.Manager


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

        Context
            .newBuilder("js")
            .option("sandbox.MaxCPUTime", "500ms")
            .option("sandbox.MaxCPUTimeCheckInterval", "5ms")
            .build()
            .use { context ->

            val listener = ExecutionListener.newBuilder()
                .onEnter { e: ExecutionEvent ->
                    println(e.location.characters)
                }
                .statements(true)
                .attach(context.engine)

            context.eval("js", content)
            listener.close()
        }

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