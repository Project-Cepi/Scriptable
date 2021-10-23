package world.cepi.luae.script.lib

import org.graalvm.polyglot.HostAccess

abstract class ScriptPoint(
    @get:HostAccess.Export
    val x: Double = 0.0,
    @get:HostAccess.Export
    val y: Double = 0.0,
    @get:HostAccess.Export
    val z: Double = 0.0
)