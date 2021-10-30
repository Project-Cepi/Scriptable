package world.cepi.luae.script

import java.util.*

object ScriptManager {

    val runningScripts = mutableMapOf<UUID, ScriptResult>()

    fun interrupt(uuid: UUID) {
        runningScripts[uuid]?.interrupt()
        runningScripts.remove(uuid)
    }

}