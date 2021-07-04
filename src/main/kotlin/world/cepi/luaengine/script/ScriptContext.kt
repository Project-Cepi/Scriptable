package world.cepi.luaengine.script

import net.minestom.server.entity.Player
import net.minestom.server.instance.Instance
import net.minestom.server.utils.Position

/**
 * Represents data a player can possibly have
 */
data class ScriptContext(
    val player: Player,
    /** What instance it was executed in */
    val instance: Instance?,
    /** Where it was executed */
    val position: Position?
)