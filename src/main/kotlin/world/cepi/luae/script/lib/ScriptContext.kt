package world.cepi.luae.script.lib

import org.graalvm.polyglot.HostAccess

/**
 * Represents data a player can possibly have
 */
data class ScriptContext(
    /** Who executed it */
    @get:HostAccess.Export
    val player: ScriptPlayer?,
    /** Where it was executed */
    @get:HostAccess.Export
    val position: ScriptPos?
)