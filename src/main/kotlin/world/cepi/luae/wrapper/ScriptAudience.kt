package world.cepi.luae.wrapper

import net.kyori.adventure.text.Component
import org.graalvm.polyglot.HostAccess
import world.cepi.kstom.adventure.asMini

interface ScriptAudience {

    @HostAccess.Export
    /** Sends a message with the mini format to this audience */
    fun sendMessage(message: String) = sendMessage(message.asMini())


    fun sendMessage(component: Component)

}