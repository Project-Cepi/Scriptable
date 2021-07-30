package world.cepi.luae.script.objects.wrapper

import net.kyori.adventure.text.Component
import net.minestom.server.entity.Player
import org.graalvm.polyglot.HostAccess

/**
 * Wrapper class for a player in a script object
 */
class ScriptPlayer(val player: Player) : ScriptAudience {

    @HostAccess.Export
    override fun sendMessage(component: Component) = player.sendMessage(component)

    @HostAccess.Export
    fun teleport(position: ScriptPosition) {
        player.teleport(position.toPosition())
    }

    @get:HostAccess.Export
    val position = player.position

    @get:HostAccess.Export
    val latency = player.latency

    @get:HostAccess.Export
    @set:HostAccess.Export
    var flying: Boolean
        get() = player.isFlying
        set(value) {
            player.isFlying = value
        }


    @get:HostAccess.Export
    val username: String = player.username

}