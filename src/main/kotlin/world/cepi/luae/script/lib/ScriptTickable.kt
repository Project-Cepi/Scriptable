package world.cepi.luae.script.lib

import net.minestom.server.Tickable
import world.cepi.luae.script.access.ScriptableExport

abstract class ScriptTickable(val tickable: Tickable) {

    @ScriptableExport
    fun tick(time: Long) = tickable.tick(time)

}