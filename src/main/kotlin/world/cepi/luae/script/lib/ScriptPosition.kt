package world.cepi.luae.script.lib

import net.minestom.server.coordinate.Pos
import org.graalvm.polyglot.HostAccess

class ScriptPosition @HostAccess.Export constructor(
    @get:HostAccess.Export
    @set:HostAccess.Export
    var x: Double,
    @get:HostAccess.Export
    @set:HostAccess.Export
    var y: Double,
    @get:HostAccess.Export
    @set:HostAccess.Export
    var z: Double,
    @get:HostAccess.Export
    @set:HostAccess.Export
    var yaw: Float,
    @get:HostAccess.Export
    @set:HostAccess.Export
    var pitch: Float
) {

    fun toPosition() = Pos(x, y, z, yaw, pitch)

    companion object {
        fun fromPosition(position: Pos): ScriptPosition =
            ScriptPosition(
                position.x(),
                position.y(),
                position.z(),
                position.yaw(),
                position.pitch()
            )
    }

}