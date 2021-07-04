package world.cepi.luaengine.command

import net.minestom.server.command.builder.Command
import net.minestom.server.command.builder.arguments.ArgumentType
import net.minestom.server.entity.Player
import world.cepi.kstom.command.addSyntax
import world.cepi.kstom.command.arguments.literal
import world.cepi.luaengine.script.Script
import world.cepi.luaengine.script.scriptString

/**
 * Subcommand for Script, allows editing of lines.
 */
object LineScriptEditor : Command("line") {

    init {
        val add = "add".literal() // Adds a new line with content
        val remove = "remove".literal() // Removes line at X index

        val content = ArgumentType.StringArray("content").map { it.joinToString(" ") }
        val index = ArgumentType.Integer("index").min(0)

        addSyntax(add, content) {
            val player = sender as Player

            val script = player.itemInMainHand.scriptString ?: return@addSyntax

            val newScript = if (script.isEmpty())
                Script(context.get(content))
            else
                Script(script + "\n${context.get(content)}")

            player.itemInMainHand = newScript.asItem()
        }

        addSyntax(remove, index) {
            val player = sender as Player

            val script = player.itemInMainHand.scriptString ?: return@addSyntax

            val newScript = Script(script.split("\n").toMutableList().also {
                it.removeAt(context.get(index))
            }.joinToString("\n"))

            player.itemInMainHand = newScript.asItem()
        }

    }

}