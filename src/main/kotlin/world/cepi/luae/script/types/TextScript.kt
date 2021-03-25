package world.cepi.luae.script.types

import world.cepi.luae.script.RunResult
import world.cepi.luae.script.Script
import world.cepi.luae.script.ScriptContext

abstract class TextScript(content: String) : Script(content) {

    abstract fun runText(scriptContext: ScriptContext, content: String): RunResult

    override fun run(scriptContext: ScriptContext): RunResult {
        if (content !is String)
            return RunResult.INVALID_CONTENT

        return runText(scriptContext, content)
    }

}