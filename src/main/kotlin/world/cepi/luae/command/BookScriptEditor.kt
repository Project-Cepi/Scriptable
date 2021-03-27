package world.cepi.luae.command

import net.kyori.adventure.inventory.Book
import net.kyori.adventure.text.Component
import net.minestom.server.command.builder.Command
import net.minestom.server.entity.Player
import world.cepi.kstom.command.addSyntax
import world.cepi.luae.script.Script

object BookScriptEditor : Command("book") {

    init {
        addSyntax { sender ->
            val player = sender as Player

            val script = player.itemInMainHand.data?.get<Script>(Script.key) ?: return@addSyntax

            val book = Book.book(
                Component.text("Script Editor"),
                Component.text(player.username),
                script.content.split("\n").map { Component.text(it) }
            )

            // TODO listen to book finish writing
            player.openBook(book)
        }
    }

}