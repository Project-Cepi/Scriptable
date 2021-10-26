package world.cepi.luae.script.lib

import net.kyori.adventure.text.Component
import net.minestom.server.entity.Player
import org.graalvm.polyglot.HostAccess
import world.cepi.luae.script.access.ScriptableExport

/**
 * Wrapper class for a player in a script object
 */
class ScriptPlayer(val player: Player) : ScriptEntity(player), ScriptAudience {

    override fun sendMessage(component: Component) = player.sendMessage(component)

    @get:ScriptableExport
    val latency = player.latency

    @get:ScriptableExport
    @set:ScriptableExport
    var flying: Boolean
        get() = player.isFlying
        set(value) {
            player.isFlying = value
        }


    @get:ScriptableExport
    val username: String = player.username

    override fun toString() = "ScriptPlayer<${entity.uuid}>"

}