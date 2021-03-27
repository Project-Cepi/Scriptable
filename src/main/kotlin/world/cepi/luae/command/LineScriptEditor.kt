package world.cepi.luae.command

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import net.minestom.server.command.builder.Command
import net.minestom.server.command.builder.arguments.ArgumentType
import net.minestom.server.entity.Player
import world.cepi.kstom.command.addSyntax
import world.cepi.kstom.command.arguments.asSubcommand
import world.cepi.luae.script.Script

/**
 * Subcommand for Script, allows editing of lines.
 */
object LineScriptEditor : Command("line") {

    init {
        val add = "add".asSubcommand() // Adds a new line with content
        val remove = "remove".asSubcommand() // Removes line at X index
        val insert = "insert".asSubcommand() // Inserts line after X index
        val list = "list".asSubcommand()

        val content = ArgumentType.String("content")
        val index = ArgumentType.Integer("index")

        addSyntax(list) { sender ->
            val player = sender as Player

            val script = player.itemInMainHand.data?.get<Script>(Script.key) ?: return@addSyntax

            script.content.split("\n").forEach {
                player.sendMessage(Component.text(it, NamedTextColor.GRAY))
            }
        }

        addSyntax(add, content) { sender, args ->
            val player = sender as Player

            val script = player.itemInMainHand.data?.get<Script>(Script.key) ?: return@addSyntax

            val newScript = Script(script.content + "\n${args.get(content)}")

            player.itemInMainHand = newScript.asItem()
        }

        addSyntax(remove, index) { sender, args ->
            val player = sender as Player

            val script = player.itemInMainHand.data?.get<Script>(Script.key) ?: return@addSyntax

            val newScript = Script(script.content.split("\n").toMutableList().also {
                it.removeAt(args.get(index))
            }.joinToString("\n"))

            player.itemInMainHand = newScript.asItem()
        }

        addSyntax(insert, index) { sender, args ->
            val player = sender as Player

            val script = player.itemInMainHand.data?.get<Script>(Script.key) ?: return@addSyntax

            val newScript = Script(script.content.split("\n").toMutableList().also {
                it.removeAt(args.get(index))
            }.joinToString("\n"))

            player.itemInMainHand = newScript.asItem()
        }

    }

}