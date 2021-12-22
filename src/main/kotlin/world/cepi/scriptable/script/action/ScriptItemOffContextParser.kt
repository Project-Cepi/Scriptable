package world.cepi.scriptable.script.action

import net.kyori.adventure.text.Component
import net.minestom.server.command.CommandSender
import net.minestom.server.command.builder.arguments.ArgumentType
import net.minestom.server.entity.Player
import world.cepi.kstom.command.arguments.context.ContextParser
import world.cepi.kstom.command.arguments.generation.CustomArgumentGenerator
import world.cepi.scriptable.script.Script
import world.cepi.scriptable.script.scriptString

object ScriptItemOffContextParser : ContextParser<Script> {

    override fun parse(sender: CommandSender) = (sender as? Player)?.itemInOffHand?.scriptString?.let { Script(it) }

    override val callbackMessage = Component.text("No script found in your off hand!")

}