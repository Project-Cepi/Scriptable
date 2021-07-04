package world.cepi.luaengine.command

import net.kyori.adventure.inventory.Book
import net.kyori.adventure.text.Component
import net.minestom.server.command.builder.Command
import net.minestom.server.entity.Player
import world.cepi.kstom.command.addSyntax
import world.cepi.luaengine.script.scriptString

object BookScriptEditor : Command("book") {

    init {
        addSyntax {
            val player = sender as Player

            val script = player.itemInMainHand.scriptString ?: return@addSyntax

            val book = Book.book(
                Component.text("Script Editor"),
                Component.text(player.username),
                script.split("\n").map { Component.text(it) }
            )

            // TODO listen to book finish writing
            player.openBook(book)
        }
    }

}