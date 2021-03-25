package world.cepi.luae.script.types

import org.graalvm.polyglot.Context
import world.cepi.luae.script.RunResult
import world.cepi.luae.script.Script
import world.cepi.luae.script.ScriptContext

class PythonScript(content: String): Script(content) {

    override fun run(scriptContext: ScriptContext): RunResult {
        Context.create().use {
                context -> context.eval("python", content)
        }

        return RunResult.SUCCESS
    }

}