package world.cepi.scriptable.script.lib

import world.cepi.scriptable.script.access.ScriptableExport

/**
 * Represents data a player can possibly have
 */
data class ScriptContext(
    /** Who executed it */
    @get:ScriptableExport
    val player: ScriptPlayer?,
    @get:ScriptableExport
    val entity: ScriptEntity?,
    /** Where it was executed */
    @get:ScriptableExport
    val position: ScriptPos?,
    @get:ScriptableExport
    val instance: ScriptInstance?
)