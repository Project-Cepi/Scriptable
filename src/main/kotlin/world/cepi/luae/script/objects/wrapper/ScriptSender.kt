package world.cepi.luae.script.objects.wrapper

import net.kyori.adventure.text.Component
import net.minestom.server.command.CommandSender
import net.minestom.server.entity.Player
import org.graalvm.polyglot.HostAccess

/**
 * Wrapper class for a player in a script object
 */
open class ScriptSender(val sender: CommandSender) : ScriptAudience {

    @HostAccess.Export
    override fun sendMessage(component: Component) = sender.sendMessage(component)

}