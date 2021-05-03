package world.cepi.luae.command

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import net.minestom.server.command.builder.Command
import net.minestom.server.command.builder.arguments.ArgumentType
import net.minestom.server.entity.Player
import world.cepi.kstom.command.addSyntax
import world.cepi.kstom.command.arguments.literal
import world.cepi.luae.script.Script
import world.cepi.luae.script.luaeScript

/**
 * Subcommand for Script, allows editing of lines.
 */
object LineScriptEditor : Command("line") {

    init {
        val add = "add".literal() // Adds a new line with content
        val remove = "remove".literal() // Removes line at X index
        val insert = "insert".literal() // Inserts line after X index
        val list = "list".literal()

        val content = ArgumentType.String("content")
        val index = ArgumentType.Integer("index")

        addSyntax(list) { sender ->
            val player = sender as Player

            val script = player.itemInMainHand.luaeScript ?: return@addSyntax

            script.content.split("\n").forEach {
                player.sendMessage(Component.text(it, NamedTextColor.GRAY))
            }
        }

        addSyntax(add, content) { sender, args ->
            val player = sender as Player

            val script = player.itemInMainHand.luaeScript ?: return@addSyntax

            val newScript = if (script.content.isEmpty())
                Script(args.get(content))
            else
                Script(script.content + "\n${args.get(content)}")

            player.itemInMainHand = newScript.asItem()
        }

        addSyntax(remove, index) { sender, args ->
            val player = sender as Player

            val script = player.itemInMainHand.luaeScript ?: return@addSyntax

            val newScript = Script(script.content.split("\n").toMutableList().also {
                it.removeAt(args.get(index))
            }.joinToString("\n"))

            player.itemInMainHand = newScript.asItem()
        }

        addSyntax(insert, index) { sender, args ->
            val player = sender as Player

            val script = player.itemInMainHand.luaeScript ?: return@addSyntax

            val newScript = Script(script.content.split("\n").toMutableList().also {
                it.removeAt(args.get(index))
            }.joinToString("\n"))

            player.itemInMainHand = newScript.asItem()
        }

    }

}