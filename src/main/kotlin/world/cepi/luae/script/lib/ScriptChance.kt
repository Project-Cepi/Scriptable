package world.cepi.luae.script.lib

import world.cepi.luae.script.access.ScriptableExport

object ScriptChance {

    @ScriptableExport
    fun chance(percent: Double): Boolean = Math.random() < percent

    @ScriptableExport
    fun random() = Math.random()

}