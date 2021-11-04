package world.cepi.scriptable.script.lib

import net.minestom.server.coordinate.Vec
import world.cepi.scriptable.script.access.ScriptableExport

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