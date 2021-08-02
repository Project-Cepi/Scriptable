package world.cepi.luae.script

import kotlinx.serialization.Serializable
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.text.format.TextDecoration
import net.minestom.server.command.CommandSender
import net.minestom.server.entity.Player
import net.minestom.server.item.ItemStack
import net.minestom.server.item.Material
import net.minestom.server.tag.Tag
import world.cepi.kstom.item.item
import world.cepi.kstom.item.withMeta
import world.cepi.luae.Luae
import world.cepi.luae.LuaeExecution
import world.cepi.luae.lib.PrintLib
import world.cepi.luae.script.lib.PrinterLibrary

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

		val env = Luae.newTable();
		Luae.installNeutralStandardLibrary(env); // Non-IO libraries basically (math, table, string, etc...)

		// Print implementation
		PrinterLibrary(scriptContext).install(env)
		PrintLib().install(env);

		val exe = Luae.newExecution(content, "test", env, null);

		val time = System.currentTimeMillis();

		while (exe.tick());
		// or: exe.run();

		if (exe.state == LuaeExecution.LuaeExecutionState.CRASHED) {
			if (exe.crashReason.errorObject is Throwable) {
				(exe.crashReason.errorObject as Throwable).printStackTrace()
			} else {
				scriptContext.sender?.sendMessage("error: " + exe.crashReason.toString())
				for (trace in exe.crashReason.stacktrace) {
					scriptContext.sender?.sendMessage(trace);
				}
			}
		} else {
			scriptContext.sender?.sendMessage("end (${System.currentTimeMillis() - time}ms)")

			for (obj in exe.returnValue) {
				scriptContext.sender?.sendMessage(obj.toString());
			}
		}

        return RunResult.Success
    }

    fun runAsSender(sender: CommandSender) {
        val runResult = run(ScriptContext(
            sender,
            (sender as? Player)?.instance,
            (sender as? Player)?.position
        ))

        when (runResult) {
            is RunResult.Success -> sender.sendMessage(Component.text("Success!", NamedTextColor.GREEN))
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