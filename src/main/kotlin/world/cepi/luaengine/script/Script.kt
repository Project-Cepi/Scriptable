package world.cepi.luaengine.script

import kotlinx.serialization.Serializable
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.text.format.TextDecoration
import net.minestom.server.item.ItemStack
import net.minestom.server.item.Material
import net.minestom.server.tag.Tag
import world.cepi.kstom.adventure.asMini
import world.cepi.kstom.item.item
import world.cepi.kstom.item.withMeta
import world.cepi.luae.*
import world.cepi.luaengine.enviorment.PrinterLibrary
import java.io.FileInputStream


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
        // Make sure LuaJProvider is loaded so Luae is implemented.
        Class.forName("world.cepi.luae.luaj.LuaJProvider")

        val env = Luae.newTable()
        Luae.installNeutralStandardLibrary(env) // Install non-io libraries

        // Print implementation
        PrinterLibrary(scriptContext).install(env)

        val exe: LuaeExecution =
            Luae.newExecution(content, "test", env, null)

        while (exe.tick());

        if (exe.state === LuaeExecution.LuaeExecutionState.CRASHED) {

            scriptContext.player.sendMessage(
                Component.text("\uEFF7")
                    .append(Component.text(" ${exe.crashReason}", NamedTextColor.RED))
            )

            if (exe.crashReason.errorObject is Throwable) {
                (exe.crashReason.errorObject as Throwable).printStackTrace()
            }

            return RunResult.ERROR
        } else {
            println("end")
            for (obj in exe.returnValue) {
                println(obj)
            }
        }

        return RunResult.SUCCESS
    }

    fun asItem(): ItemStack = item(Material.PAPER) {
        displayName(Component.text("Script", NamedTextColor.GREEN)
            .decoration(TextDecoration.ITALIC, false))

        withMeta {
            this.setTag(Tag.String(key), content)
        }
    }


}

val ItemStack.scriptString: String?
    get() = this.meta.getTag(Tag.String(Script.key))