package world.cepi.luae.script.lib

import net.minestom.server.Tickable

open class ScriptTickable(val tickable: Tickable) {

    fun tick(time: Long) = tickable.tick(time)

}