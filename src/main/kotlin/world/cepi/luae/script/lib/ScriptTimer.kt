package world.cepi.luae.script.lib

import world.cepi.luae.script.access.ScriptableExport

object ScriptTimer {

    @ScriptableExport
    fun sleep(time: Long) = Thread.sleep(time)

}