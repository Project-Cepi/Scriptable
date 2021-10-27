package world.cepi.luae.script.lib

import world.cepi.luae.script.access.ScriptableExport

object ScriptChance {

    @ScriptableExport
    fun chance(percent: Double) = Math.random() < percent

}