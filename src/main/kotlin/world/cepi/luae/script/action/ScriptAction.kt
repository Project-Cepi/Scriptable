package world.cepi.luae.script.action

import net.minestom.server.entity.Entity
import world.cepi.actions.Action
import world.cepi.kstom.command.arguments.generation.annotations.ParameterContext
import world.cepi.luae.script.Script

class ScriptAction(
    @ParameterContext(ScriptItemMainContextParser::class)
    val script: String
) : Action() {

    override fun invoke(source: Entity, target: Entity?) {
        Script(script).runAsEntity(source)
    }

}