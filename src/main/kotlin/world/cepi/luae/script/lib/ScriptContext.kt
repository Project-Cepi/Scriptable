package world.cepi.luae.script.lib

import net.minestom.server.command.CommandSender
import net.minestom.server.coordinate.Pos
import net.minestom.server.entity.Player
import net.minestom.server.instance.Instance
import org.graalvm.polyglot.HostAccess
import world.cepi.luae.script.lib.ScriptPlayer
import world.cepi.luae.script.lib.ScriptPosition

/**
 * Represents data a player can possibly have
 */
data class ScriptContext(
    /** Who executed it */
    @get:HostAccess.Export
    val player: ScriptPlayer?,
    /** Where it was executed */
    @get:HostAccess.Export
    val position: ScriptPosition?
)