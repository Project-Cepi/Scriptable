package world.cepi.scriptable.script.lib

import net.kyori.adventure.audience.Audience
import world.cepi.kstom.adventure.asMini
import world.cepi.scriptable.script.access.ScriptableExport

interface ScriptAudience {

    val audience: Audience

    /** Sends a message with the mini format to this audience */
    @ScriptableExport
    fun sendMessage(message: String) = audience.sendMessage(message.asMini())

    @ScriptableExport
    fun sendMessage(int: Int) = sendMessage(int.toString())

    @ScriptableExport
    fun sendMessage(double: Double) = sendMessage(double.toString())

    @ScriptableExport
    fun sendMessage(message: ScriptComponent) = audience.sendMessage(message.component)

    @ScriptableExport
    fun sendActionBar(message: String) = audience.sendActionBar(message.asMini())

    @ScriptableExport
    fun sendActionBar(message: ScriptComponent) = audience.sendActionBar(message.component)

}