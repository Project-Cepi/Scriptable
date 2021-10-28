package world.cepi.luae.script.lib

import world.cepi.kstom.Manager
import world.cepi.luae.script.access.ScriptableExport

class ScriptExecutor(val context: ScriptContext) {

    @ScriptableExport
    fun execute(command: String) = context.player?.player?.let { Manager.command.execute(it, command) }

}