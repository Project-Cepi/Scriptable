package world.cepi.scriptable.script.action

import net.minestom.server.command.CommandSender
import net.minestom.server.command.builder.arguments.Argument
import net.minestom.server.command.builder.arguments.ArgumentString
import net.minestom.server.command.builder.arguments.ArgumentType
import net.minestom.server.entity.Player
import world.cepi.kstom.command.arguments.context.ContextParser
import world.cepi.scriptable.script.scriptString

object ScriptItemOffContextParser : ContextParser<String> {

    override fun or(): Argument<out String> = ArgumentType.String("scriptContext")

    override fun parse(sender: CommandSender): String? =
        (sender as? Player)?.itemInOffHand?.scriptString

}