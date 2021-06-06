package world.cepi.luae.wrapper

import net.minestom.server.entity.Player
import org.graalvm.polyglot.HostAccess
import world.cepi.kstom.adventure.asMini

/**
 * Wrapper class for a player in a script object
 */
class ScriptPlayer(val player: Player) {

    @HostAccess.Export
    fun sendMessage(message: String) {
        player.sendMessage(message.asMini())
    }

    @HostAccess.Export
    val username: String = player.username

}