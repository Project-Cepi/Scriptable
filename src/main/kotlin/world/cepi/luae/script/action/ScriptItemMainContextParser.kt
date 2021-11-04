package world.cepi.luae.script.action

import net.minestom.server.command.CommandSender
import net.minestom.server.entity.Player
import net.minestom.server.item.ItemStack
import world.cepi.kstom.command.arguments.context.ContextParser
import world.cepi.luae.script.scriptString

object ScriptItemMainContextParser : ContextParser<String> {

    override fun parse(sender: CommandSender): String? =
        (sender as? Player)?.itemInMainHand?.scriptString

}