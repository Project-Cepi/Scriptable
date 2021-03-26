package world.cepi.luae.command

import net.minestom.server.command.builder.Command
import net.minestom.server.entity.Player
import world.cepi.kstom.command.arguments.asSubcommand
import world.cepi.kstom.command.addSyntax
import world.cepi.luae.script.Script
import world.cepi.luae.script.ScriptContext

/**
 * Create and manage item scripts.
 */
object ScriptCommand : Command("script") {

    init {
        val create = "create".asSubcommand()
        val run = "run".asSubcommand()

        addSyntax(create) { sender ->
            val player = sender as Player

            player.inventory.addItemStack(Script().asItem())
        }

        addSyntax(run) { sender ->
            val player = sender as Player

            val script = player.itemInMainHand.data?.get<Script>(Script.key) ?: return@addSyntax

            script.run(ScriptContext(
                player,
                player.instance,
                player.position
            ))
        }

    }

}