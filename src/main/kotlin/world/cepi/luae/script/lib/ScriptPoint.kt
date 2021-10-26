package world.cepi.luae.script.lib

import net.minestom.server.coordinate.Vec
import org.graalvm.polyglot.HostAccess
import world.cepi.luae.script.access.ScriptableExport

abstract class ScriptPoint(
    @get:ScriptableExport
    val x: Double = 0.0,
    @get:ScriptableExport
    val y: Double = 0.0,
    @get:ScriptableExport
    val z: Double = 0.0
) {
    fun toVec() = Vec(x, y, z)
}