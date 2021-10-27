package world.cepi.luae.script.lib

import net.kyori.adventure.audience.Audience
import net.minestom.server.entity.Player
import world.cepi.luae.script.access.ScriptableExport

/**
 * Wrapper class for a player in a script object
 */
class ScriptPlayer(val player: Player) : ScriptEntity(player), ScriptAudience {

    override val audience = player

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