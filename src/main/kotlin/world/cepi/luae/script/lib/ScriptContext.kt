package world.cepi.luae.script.lib

import org.graalvm.polyglot.HostAccess
import world.cepi.luae.script.access.ScriptableExport

/**
 * Represents data a player can possibly have
 */
data class ScriptContext(
    /** Who executed it */
    @get:ScriptableExport
    val player: ScriptPlayer?,
    /** Where it was executed */
    @get:ScriptableExport
    val position: ScriptPos?
)