package world.cepi.scriptable.command

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.event.ClickEvent
import net.kyori.adventure.text.format.NamedTextColor
import net.minestom.server.command.builder.arguments.ArgumentType
import world.cepi.kstom.command.arguments.literal
import world.cepi.kstom.command.kommand.Kommand
import world.cepi.scriptable.script.Script
import world.cepi.scriptable.script.ScriptManager
import world.cepi.scriptable.script.scriptString

/**
 * Create and manage item scripts.
 */
object ScriptCommand : Kommand({

    val create by literal
    val run by literal
    val content by literal
    val quick by literal
    val all by literal
    val interrupt by literal

    val uuid = ArgumentType.UUID("uuid")

    val quickContent = ArgumentType.StringArray("quickContent").map {
        it.joinToString(" ")
    }

    syntax(content) {
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

        Script(script).runAsPlayer(player, debug = true)
    }

    syntax(quick, quickContent) {
        Script(!quickContent).runAsPlayer(player, debug = true)
    }

    syntax(all) {
        sender.sendMessage(Component.text("Running Scripts ", NamedTextColor.GRAY)
            .append(Component.text("(${ScriptManager.runningScripts.size}) ", NamedTextColor.BLUE))
            .append(Component.text("-", NamedTextColor.GRAY)))

        ScriptManager.runningScripts.forEach {
            sender.sendMessage(
                Component.text("Running for ", NamedTextColor.GRAY)
                    .append(
                        Component.text((System.currentTimeMillis() - it.value.created.toEpochMilli()) / 1000, NamedTextColor.BLUE)
                            .append(Component.text("s"))
                    )
                    .append(Component.text(" [steps -> ${it.value.steps.get()}]", NamedTextColor.GOLD))
                    .append(
                        Component.text(" (X)", NamedTextColor.RED)
                            .clickEvent(ClickEvent.runCommand("/script interrupt ${it.key}"))
                    )
            )
        }
    }

    syntax(interrupt, uuid) {
        ScriptManager.runningScripts[!uuid]?.interrupt() ?: run {
            sender.sendMessage("No script found")
            return@syntax
        }

        sender.sendMessage("Stopped script!")
    }

    addSubcommands(BookScriptEditor)

    LineScriptEditor.k(this)

}, "script")