package world.cepi.luae.script.lib

import net.minestom.server.coordinate.Vec
import org.graalvm.polyglot.HostAccess

class ScriptVec(
    x: Double,
    y: Double,
    z: Double
) : ScriptPoint(x, y, z) {

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