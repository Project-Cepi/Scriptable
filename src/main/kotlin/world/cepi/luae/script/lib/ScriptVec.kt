package world.cepi.luae.script.lib

import net.minestom.server.coordinate.Vec
import org.graalvm.polyglot.HostAccess

data class ScriptVec(
    @get:HostAccess.Export
    @set:HostAccess.Export
    var x: Double,
    @get:HostAccess.Export
    @set:HostAccess.Export
    var y: Double,
    @get:HostAccess.Export
    @set:HostAccess.Export
    var z: Double
) {

    fun toVec() = Vec(x, y, z)

    companion object {

        @HostAccess.Export
        fun new(x: Double, y: Double, z: Double) =
            ScriptVec(x, y, z)


        fun fromVec(vec: Vec): ScriptVec =
            ScriptVec(
                vec.x(),
                vec.y(),
                vec.z()
            )
    }

}