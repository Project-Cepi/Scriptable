package world.cepi.luae.script.lib

import net.kyori.adventure.text.Component
import org.graalvm.polyglot.HostAccess
import world.cepi.kstom.adventure.asMini

interface ScriptAudience {

    /** Sends a message with the mini format to this audience */
    @HostAccess.Export
    fun sendMessage(message: String) = sendMessage(message.asMini())

    fun sendMessage(component: Component)

}