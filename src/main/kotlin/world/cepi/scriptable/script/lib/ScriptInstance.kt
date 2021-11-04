package world.cepi.scriptable.script.lib

import net.minestom.server.instance.Instance
import world.cepi.scriptable.script.access.ScriptableExport

class ScriptInstance(val instance: Instance) : ScriptTickable(instance) {

    @get:ScriptableExport
    @set:ScriptableExport
    var time: Long
        get() = instance.time
        set(value) {
            instance.time = value
        }

    @ScriptableExport
    fun blockAt(point: ScriptPoint) = ScriptBlock(instance.getBlock(point.toVec()))

    @ScriptableExport
    fun setBlock(point: ScriptPoint, block: ScriptBlock) {
        instance.setBlock(point.toVec(), block.block)
    }

    @get:ScriptableExport
    val players: Set<ScriptPlayer>
        get() = instance.players.map { ScriptPlayer(it) }.toSet()

    @get:ScriptableExport
    val entities: Set<ScriptEntity>
        get() = instance.entities.map { ScriptEntity(it) }.toSet()

}