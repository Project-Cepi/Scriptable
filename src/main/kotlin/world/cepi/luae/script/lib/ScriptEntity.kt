package world.cepi.luae.script.lib

import net.minestom.server.entity.Entity
import org.graalvm.polyglot.HostAccess

/**
 * Wrapper class for a player in a script object
 */
open class ScriptEntity(val entity: Entity) {

    @HostAccess.Export
    fun teleport(position: ScriptPos) {
        entity.teleport(position.toPosition())
    }

    @get:HostAccess.Export
    @set:HostAccess.Export
    var position
        get() = ScriptPos.fromPosition(entity.position)
        set(value) {
            entity.teleport(value.toPosition())
        }

    @get:HostAccess.Export
    @set:HostAccess.Export
    var velocity
        get() = ScriptVec.fromVec(entity.velocity)
        set(value) {
            entity.velocity = value.toVec()
        }

    @JvmOverloads
    @HostAccess.Export
    fun nearestEntities(distanceCap: Double = Double.MAX_VALUE): List<ScriptEntity>? =
        entity.instance?.entities
            ?.filter { it != entity }
            ?.filter { it.getDistance(entity) <= distanceCap }
            ?.sortedBy { it.getDistance(entity) }?.map { ScriptEntity(it) }

    @JvmOverloads
    @HostAccess.Export
    fun nearestEntity(distanceCap: Double = Double.MAX_VALUE): ScriptEntity? =
        nearestEntities(distanceCap)?.first()

}