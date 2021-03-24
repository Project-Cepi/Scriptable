package world.cepi.luae.script.types

import org.graalvm.polyglot.Context
import world.cepi.luae.script.Script
import world.cepi.luae.script.ScriptContext

class JSScript : Script {

    override fun run(scriptContext: ScriptContext, content: String) {
        Context.create().use {
                context -> context.eval("js", content)
        }
    }
}