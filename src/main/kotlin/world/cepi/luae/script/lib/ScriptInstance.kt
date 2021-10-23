package world.cepi.luae.script.lib

import net.minestom.server.instance.Instance
import org.graalvm.polyglot.HostAccess

class ScriptInstance(val instance: Instance) : ScriptTickable(instance) {

    @get:HostAccess.Export
    @set:HostAccess.Export
    var time: Long
        get() = instance.time
        set(value) {
            instance.time = value
        }

    @HostAccess.Export
    fun blockAt(point: ScriptPoint) = ScriptBlock(instance.getBlock(point.toVec()))

    @get:HostAccess.Export
    val players: Set<ScriptPlayer>
        get() = instance.players.map { ScriptPlayer(it) }.toSet()

    @get:HostAccess.Export
    val entities: Set<ScriptEntity>
        get() = instance.entities.map { ScriptEntity(it) }.toSet()

}