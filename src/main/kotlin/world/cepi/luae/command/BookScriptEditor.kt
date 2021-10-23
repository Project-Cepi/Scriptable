package world.cepi.luae.command

import net.kyori.adventure.inventory.Book
import net.kyori.adventure.text.Component
import world.cepi.kstom.command.kommand.Kommand
import world.cepi.luae.script.scriptString

object BookScriptEditor : Kommand({

    syntax {
        val script = player.itemInMainHand.scriptString ?: return@syntax

        val book = Book.book(
            Component.text("Script Editor"),
            Component.text(player.username),
            script.split("\n").map { Component.text(it) }
        )

        // TODO listen to book finish writing
        player.openBook(book)
    }


}, "book")