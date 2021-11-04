package world.cepi.scriptable.script.lib

import world.cepi.scriptable.script.access.ScriptableExport

object ScriptChance {

    @ScriptableExport
    fun chance(percent: Double): Boolean = Math.random() < percent

    @ScriptableExport
    fun random() = Math.random()

}