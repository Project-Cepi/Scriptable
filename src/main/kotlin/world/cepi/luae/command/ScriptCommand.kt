package world.cepi.luae.command

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import net.minestom.server.command.builder.arguments.ArgumentType
import world.cepi.kstom.command.arguments.literal
import world.cepi.kstom.command.kommand.Kommand
import world.cepi.luae.script.Script
import world.cepi.luae.script.scriptString

/**
 * Create and manage item scripts.
 */
object ScriptCommand : Kommand({

    val create = "create".literal()
    val run = "run".literal()
    val list = "list".literal()
    val quick = "quick".literal()
    val quickContent = ArgumentType.StringArray("quickContent").map {
        it.joinToString(" ")
    }

    syntax(list) {
        val script = player.itemInMainHand.scriptString ?: return@syntax

        script.split("\n").forEach {
            player.sendMessage(Component.text(it, NamedTextColor.GRAY))
        }
    }

    syntax(create) {
        player.inventory.addItemStack(Script().asItem())
    }

    syntax(run) {
        val script = player.itemInMainHand.scriptString ?: return@syntax

        Script(script).runAsPlayer(player)
    }

    syntax(quick, quickContent) {
        Script(!quickContent).runAsPlayer(player)
    }

    addSubcommands(BookScriptEditor)

    LineScriptEditor.k(this)

}, "script")