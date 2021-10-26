package world.cepi.luae.script.lib

import net.minestom.server.coordinate.Vec
import org.graalvm.polyglot.HostAccess
import world.cepi.luae.script.access.ScriptableExport

class ScriptVec(
    x: Double,
    y: Double,
    z: Double
) : ScriptPoint(x, y, z) {

    companion object {

        @ScriptableExport
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