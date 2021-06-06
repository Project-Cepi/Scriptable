package world.cepi.luae.script

import net.minestom.server.entity.Player
import net.minestom.server.instance.Instance
import net.minestom.server.utils.Position
import org.graalvm.polyglot.HostAccess
import world.cepi.luae.wrapper.ScriptPlayer

/**
 * Represents data a player can possibly have
 */
data class ScriptContext(
    /** Who executed it */
    @get:HostAccess.Export
    val player: ScriptPlayer?,
    /** What instance it was executed in */
    val instance: Instance?,
    /** Where it was executed */
    val position: Position?
) {
    constructor(player: Player?, instance: Instance?, position: Position?) :
        this(player?.let { ScriptPlayer(it) }, instance, position)

}