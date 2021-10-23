package world.cepi.luae.script.lib

import net.minestom.server.coordinate.Pos
import org.graalvm.polyglot.HostAccess

class ScriptPos(
    x: Double,
    y: Double,
    z: Double,
    @get:HostAccess.Export
    val yaw: Float,
    @get:HostAccess.Export
    val pitch: Float
) : ScriptPoint(x, y, z) {

    fun toPosition() = Pos(x, y, z, yaw, pitch)

    companion object {

        @JvmOverloads
        @HostAccess.Export
        fun new(x: Double, y: Double, z: Double, yaw: Float = 0f, pitch: Float = 0f) =
            ScriptPos(x, y, z, yaw, pitch)


        fun fromPosition(position: Pos): ScriptPos =
            ScriptPos(
                position.x(),
                position.y(),
                position.z(),
                position.yaw(),
                position.pitch()
            )
    }

}