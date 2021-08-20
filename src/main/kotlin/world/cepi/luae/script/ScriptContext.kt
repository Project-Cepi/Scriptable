package world.cepi.luae.script

import net.minestom.server.command.CommandSender
import net.minestom.server.coordinate.Pos
import net.minestom.server.instance.Instance

/**
 * Represents data a player can possibly have
 */
data class ScriptContext(
    /** Who executed it */
    val sender: CommandSender?,
    /** What instance it was executed in */
    val instance: Instance?,
    /** Where it was executed */
    val position: Pos?
)