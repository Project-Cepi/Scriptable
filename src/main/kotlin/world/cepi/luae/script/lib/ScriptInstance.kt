package world.cepi.luae.script.lib

import net.minestom.server.entity.Entity
import net.minestom.server.entity.Player
import net.minestom.server.instance.Instance
import org.graalvm.polyglot.HostAccess

class ScriptInstance(val instance: Instance) {

    @get:HostAccess.Export
    @set:HostAccess.Export
    var time: Long
        get() = instance.time
        set(value) {
            instance.time = value
        }

    @get:HostAccess.Export
    val players: Set<Player>
        get() = instance.players

    @get:HostAccess.Export
    val entities: Set<Entity>
        get() = instance.entities

}