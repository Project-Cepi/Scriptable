package world.cepi.luae.command

import net.minestom.server.command.builder.Command
import net.minestom.server.entity.Player
import world.cepi.kstom.command.addSyntax
import world.cepi.kstom.command.arguments.literal
import world.cepi.luae.script.Script
import world.cepi.luae.script.ScriptContext
import world.cepi.luae.script.luaeScript

/**
 * Create and manage item scripts.
 */
object ScriptCommand : Command("script") {

    init {
        val create = "create".literal()
        val run = "run".literal()

        addSyntax(create) { sender ->
            val player = sender as Player

            player.inventory.addItemStack(Script().asItem())
        }

        addSyntax(run) { sender ->
            val player = sender as Player

            val script = player.itemInMainHand.luaeScript ?: return@addSyntax

            script.run(ScriptContext(
                player,
                player.instance,
                player.position
            ))
        }

        addSubcommand(object : Command("editor") {
            init {
                addSubcommand(BookScriptEditor)
                addSubcommand(LineScriptEditor)
            }
        })

    }

}