package world.cepi.scriptable.script.action

import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient
import net.minestom.server.command.builder.arguments.Argument
import net.minestom.server.command.builder.arguments.ArgumentType
import net.minestom.server.entity.Entity
import net.minestom.server.entity.Player
import world.cepi.actions.Action
import world.cepi.kstom.command.arguments.defaultValue
import world.cepi.kstom.command.arguments.generation.ChosenArgumentGeneration
import world.cepi.kstom.command.arguments.generation.CustomArgumentGeneration
import world.cepi.kstom.command.arguments.generation.annotations.CustomArgument
import world.cepi.kstom.command.arguments.generation.annotations.DefaultBoolean
import world.cepi.kstom.command.arguments.generation.annotations.ParameterContext
import world.cepi.kstom.command.kommand.Kommand
import world.cepi.scriptable.script.Script
import world.cepi.scriptable.script.lib.ScriptEntity
import world.cepi.scriptable.script.scriptString

@Serializable
class ScriptAction(
    val debug: Boolean = false,
    val script: Script
) : Action() {

    companion object : CustomArgumentGeneration<ScriptAction> {
        override val argumentsForGeneration: List<List<Argument<*>>> = listOf(listOf())

        override fun generate(syntax: Kommand.SyntaxContext, args: List<String>, index: Int): ScriptAction {
            return (syntax.sender as? Player)?.itemInOffHand?.scriptString?.let { ScriptAction(false, Script(it)) }
                ?: throw IllegalStateException("Player needs to hold a script in their main hand")
        }
    }

    override fun invoke(source: Entity, target: Entity?) {
        script.runAsEntity(source, target?.let { mapOf("target" to ScriptEntity(it)) } ?: emptyMap(), debug)
    }

}