package world.cepi.scriptable.script.action

import kotlinx.serialization.Serializable
import net.minestom.server.entity.Entity
import world.cepi.actions.Action
import world.cepi.kstom.command.arguments.generation.annotations.DefaultBoolean
import world.cepi.kstom.command.arguments.generation.annotations.ParameterContext
import world.cepi.scriptable.script.Script

@Serializable
class ScriptAction(
    @DefaultBoolean(true)
    val debug: Boolean = true,
    @ParameterContext(ScriptItemOffContextParser::class)
    val script: String
) : Action() {

    override fun invoke(source: Entity, target: Entity?) {
        Script(script).runAsEntity(source, debug)
    }

}