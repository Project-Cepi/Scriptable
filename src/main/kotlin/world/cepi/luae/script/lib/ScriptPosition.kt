package world.cepi.luae.script.lib

import net.minestom.server.coordinate.Pos
import org.graalvm.polyglot.HostAccess

class ScriptPosition(
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

        @JvmOverloads
        @HostAccess.Export
        fun new(x: Double, y: Double, z: Double, yaw: Float = 0f, pitch: Float = 0f) =
            ScriptPosition(x, y, z, yaw, pitch)


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