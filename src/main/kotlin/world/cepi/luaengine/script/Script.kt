package world.cepi.luaengine.script

import kotlinx.serialization.Serializable
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.text.format.TextDecoration
import net.minestom.server.item.ItemStack
import net.minestom.server.item.Material
import net.minestom.server.tag.Tag
import world.cepi.kstom.item.item
import world.cepi.kstom.item.withMeta
import world.cepi.luae.*
import java.io.FileInputStream


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

        /*
		 * Testing driver for Luae
		 */

        // Make sure LuaJProvider is loaded so Luae is implemented.
        Class.forName("world.cepi.luae.luaj.LuaJProvider")

        val env: LuaeTable = Luae.newTable()
        Luae.installNeutralStandardLibrary(env) // Non-IO libraries basically

        // Print implementation
        env.set("print", object : LuaeExternalFunction {
            override fun call(context: LuaeContext, args: LuaeVarargs) {
                for (arg in args) {
                    scriptContext.player.sendMessage("$arg")
                }
                context.returnFunction() // Don't forget this.
            }
        })

        val exe: LuaeExecution =
            Luae.newExecution(content, "test", env, null)

        while (exe.tick());

        if (exe.state === LuaeExecution.LuaeExecutionState.CRASHED) {
            if (exe.crashReason.errorObject is Throwable) {
                (exe.crashReason.errorObject as Throwable).printStackTrace()
            } else {
                println("error: " + exe.crashReason.toString())
            }
        } else {
            println("end")
            for (obj in exe.returnValue) {
                println(obj)
            }
        }

            return RunResult.SUCCESS
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