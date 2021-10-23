package world.cepi.luae.script.lib

import net.minestom.server.coordinate.Vec
import org.graalvm.polyglot.HostAccess

abstract class ScriptPoint(
    @get:HostAccess.Export
    val x: Double = 0.0,
    @get:HostAccess.Export
    val y: Double = 0.0,
    @get:HostAccess.Export
    val z: Double = 0.0
) {
    fun toVec() = Vec(x, y, z)
}