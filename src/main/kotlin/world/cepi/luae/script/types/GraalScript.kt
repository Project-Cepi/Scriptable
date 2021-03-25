package world.cepi.luae.script.types

import org.graalvm.polyglot.Context
import world.cepi.luae.script.RunResult
import world.cepi.luae.script.ScriptContext

abstract class GraalScript(val scriptName: String, content: String): TextScript(content) {

    override fun runText(scriptContext: ScriptContext, content: String): RunResult {
        Context.create().use {
                context -> context.eval(scriptName, content)
        }

        return RunResult.SUCCESS
    }

}