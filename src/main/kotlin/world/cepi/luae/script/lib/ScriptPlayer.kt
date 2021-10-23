package world.cepi.luae.script.lib

import net.kyori.adventure.text.Component
import net.minestom.server.entity.Player
import org.graalvm.polyglot.HostAccess

/**
 * Wrapper class for a player in a script object
 */
class ScriptPlayer(val player: Player) : ScriptAudience {

    override fun sendMessage(component: Component) = player.sendMessage(component)

    @HostAccess.Export
    fun teleport(position: ScriptPos) {
        player.teleport(position.toPosition())
    }

    @get:HostAccess.Export
    @set:HostAccess.Export
    var position
        get() = ScriptPos.fromPosition(player.position)
        set(value) {
            player.teleport(value.toPosition())
        }

    @get:HostAccess.Export
    @set:HostAccess.Export
    var velocity
        get() = ScriptVec.fromVec(player.velocity)
        set(value) {
            player.velocity = value.toVec()
        }

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