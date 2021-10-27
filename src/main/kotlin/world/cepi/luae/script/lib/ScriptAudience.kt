package world.cepi.luae.script.lib

import net.kyori.adventure.audience.Audience
import net.kyori.adventure.text.Component
import org.graalvm.polyglot.HostAccess
import world.cepi.kstom.adventure.asMini
import world.cepi.luae.script.access.ScriptableExport

interface ScriptAudience {

    val audience: Audience

    /** Sends a message with the mini format to this audience */
    @ScriptableExport
    fun sendMessage(message: String) = audience.sendMessage(message.asMini())

    @ScriptableExport
    fun sendActionBar(message: String) = audience.sendActionBar(message.asMini())

}