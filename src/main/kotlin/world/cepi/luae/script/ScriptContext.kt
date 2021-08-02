package world.cepi.luae.script

import net.minestom.server.command.CommandSender
import net.minestom.server.entity.Player
import net.minestom.server.instance.Instance
import net.minestom.server.utils.Position
import org.graalvm.polyglot.HostAccess
import world.cepi.luae.script.objects.wrapper.ScriptPlayer
import world.cepi.luae.script.objects.wrapper.ScriptSender

/**
 * Represents data a player can possibly have
 */
data class ScriptContext(
    /** Who executed it */
    @get:HostAccess.Export
    val sender: ScriptSender?,
    /** What instance it was executed in */
    val instance: Instance?,
    /** Where it was executed */
    val position: Position?
) {
    constructor(sender: CommandSender?, instance: Instance?, position: Position?) :
        this(sender?.let { ScriptSender(it) }, instance, position)

}