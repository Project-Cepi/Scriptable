package world.cepi.luae.script.types

import org.graalvm.polyglot.Context
import world.cepi.luae.script.RunResult
import world.cepi.luae.script.ScriptContext

class JSScript : TextScript {

    override fun runText(scriptContext: ScriptContext, content: String): RunResult {
        Context.create().use {
                context -> context.eval("js", content)
        }

        return RunResult.SUCCESS
    }
}