package world.cepi.luae.script.lib

import net.minestom.server.entity.Entity
import org.graalvm.polyglot.HostAccess

/**
 * Wrapper class for a player in a script object
 */
open class ScriptEntity(val entity: Entity) : ScriptTickable(entity) {

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

    @HostAccess.Export
    fun onGround() = entity.isOnGround

    @HostAccess.Export
    fun onFire() = entity.isOnFire

    @HostAccess.Export
    fun onFire(flag: Boolean) {
        entity.isOnFire = true
    }

    @get:HostAccess.Export
    @set:HostAccess.Export
    var glowing
        get() = entity.isGlowing
        set(value) {
            entity.isGlowing = value
        }

    @HostAccess.Export
    fun remove() = entity.remove()

    @get:HostAccess.Export
    @set:HostAccess.Export
    var instance: ScriptInstance?
        get() = entity.instance?.let { ScriptInstance(it) }
        set(value) {
            entity.setInstance(value!!.instance)
        }

    @get:HostAccess.Export
    val passengers: Set<ScriptEntity>
        get() = entity.passengers.map { ScriptEntity(it) }.toSet()

    @HostAccess.Export
    fun addPassenger(passenger: ScriptEntity) {
        entity.addPassenger(passenger.entity)
    }

    @HostAccess.Export
    fun removePassenger(passenger: ScriptEntity) {
        entity.removePassenger(passenger.entity)
    }

    @get:HostAccess.Export
    val vehicle: ScriptEntity?
        get() = entity.vehicle?.let { ScriptEntity(it) }

    @JvmOverloads
    @HostAccess.Export
    fun nearestEntities(distanceCap: Double = Double.MAX_VALUE): Set<ScriptEntity>? =
        entity.instance?.entities
            ?.filter { it != entity }
            ?.filter { it.getDistance(entity) <= distanceCap }
            ?.sortedBy { it.getDistance(entity) }?.map { ScriptEntity(it) }
            ?.toSet()

    @JvmOverloads
    @HostAccess.Export
    fun nearestEntity(distanceCap: Double = Double.MAX_VALUE): ScriptEntity? =
        nearestEntities(distanceCap)?.first()

    override fun toString() = "ScriptEntity<${entity.entityType.namespace().path}, ${entity.uuid}>"

}