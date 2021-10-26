package world.cepi.luae.script.lib

import net.minestom.server.entity.Entity
import org.graalvm.polyglot.HostAccess
import world.cepi.luae.script.access.ScriptableExport

/**
 * Wrapper class for a player in a script object
 */
open class ScriptEntity(val entity: Entity) : ScriptTickable(entity) {

    @ScriptableExport
    fun teleport(position: ScriptPos) {
        entity.teleport(position.toPosition())
    }

    @get:ScriptableExport
    @set:ScriptableExport
    var position
        get() = ScriptPos.fromPosition(entity.position)
        set(value) {
            entity.teleport(value.toPosition())
        }

    @get:ScriptableExport
    @set:ScriptableExport
    var velocity
        get() = ScriptVec.fromVec(entity.velocity)
        set(value) {
            entity.velocity = value.toVec()
        }

    @get:ScriptableExport
    val onGround get() = entity.isOnGround

    @get:ScriptableExport
    var onFire: Boolean
        get() = entity.isOnFire
        set(value) {
            entity.isOnFire = true
        }

    @get:ScriptableExport
    @set:ScriptableExport
    var glowing
        get() = entity.isGlowing
        set(value) {
            entity.isGlowing = value
        }

    @ScriptableExport
    fun remove() = entity.remove()

    @get:ScriptableExport
    @set:ScriptableExport
    var instance: ScriptInstance?
        get() = entity.instance?.let { ScriptInstance(it) }
        set(value) {
            entity.setInstance(value!!.instance)
        }

    @get:ScriptableExport
    val isOnGround get() = entity.isOnGround

    @get:ScriptableExport
    val passengers: Set<ScriptEntity>
        get() = entity.passengers.map { ScriptEntity(it) }.toSet()

    @ScriptableExport
    fun addPassenger(passenger: ScriptEntity) {
        entity.addPassenger(passenger.entity)
    }

    @ScriptableExport
    fun removePassenger(passenger: ScriptEntity) {
        entity.removePassenger(passenger.entity)
    }

    @get:ScriptableExport
    val vehicle: ScriptEntity?
        get() = entity.vehicle?.let { ScriptEntity(it) }

    @JvmOverloads
    @ScriptableExport
    fun nearestEntities(distanceCap: Double = Double.MAX_VALUE): Set<ScriptEntity>? =
        entity.instance?.entities
            ?.asSequence()
            ?.filter { it != entity }
            ?.filter { it.getDistance(entity) <= distanceCap }
            ?.sortedBy { it.getDistance(entity) }?.map { ScriptEntity(it) }
            ?.toSet()

    @JvmOverloads
    @ScriptableExport
    fun nearestEntity(distanceCap: Double = Double.MAX_VALUE): ScriptEntity? =
        nearestEntities(distanceCap)?.first()

    override fun toString() = "ScriptEntity<${entity.entityType.namespace().path}, ${entity.uuid}>"

}