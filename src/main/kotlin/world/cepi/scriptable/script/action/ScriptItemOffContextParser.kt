package world.cepi.scriptable.script.action

import net.minestom.server.command.builder.arguments.ArgumentType
import world.cepi.kstom.command.arguments.generation.CustomArgumentGenerator

object ScriptItemOffContextParser : CustomArgumentGenerator {

    override fun new(id: String, annotations: List<Annotation>) = ArgumentType.StringArray("script")
        .map { it.joinToString(" ") }

}