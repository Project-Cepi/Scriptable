package world.cepi.scriptable.script.lib

import world.cepi.scriptable.script.access.ScriptableExport

object ScriptTimer {

    @ScriptableExport
    fun sleep(time: Long) = Thread.sleep(time)

}