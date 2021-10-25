package world.cepi.luae.script.lib

import net.minestom.server.Tickable

abstract class ScriptTickable(val tickable: Tickable) {

    fun tick(time: Long) = tickable.tick(time)

}