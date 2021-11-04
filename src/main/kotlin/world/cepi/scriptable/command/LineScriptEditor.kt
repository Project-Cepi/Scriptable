package world.cepi.scriptable.command

import net.minestom.server.command.builder.arguments.ArgumentType
import world.cepi.kstom.command.arguments.literal
import world.cepi.kstom.command.kommand.Kommand
import world.cepi.scriptable.script.Script
import world.cepi.scriptable.script.scriptString

/**
 * Subcommand for Script, allows editing of lines.
 */
object LineScriptEditor : Kommand({

    val add = "add".literal() // Adds a new line with content
    val remove = "remove".literal() // Removes line at X index

    val content = ArgumentType.StringArray("content").map { it.joinToString(" ") }
    val index = ArgumentType.Integer("index").min(0)

    syntax(add, content) {
        val script = player.itemInMainHand.scriptString ?: return@syntax

        val newScript = if (script.isEmpty())
            Script(!content)
        else
            Script(script + "\n${!content}")

        player.itemInMainHand = newScript.asItem()
    }

    syntax(remove, index) {
        val script = player.itemInMainHand.scriptString ?: return@syntax

        val newScript = Script(script.split("\n").toMutableList().also {
            it.removeAt(!index)
        }.joinToString("\n"))

        player.itemInMainHand = newScript.asItem()
    }

}, "line")