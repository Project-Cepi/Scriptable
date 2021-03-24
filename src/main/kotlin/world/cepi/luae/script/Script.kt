package world.cepi.luae.script

import world.cepi.luae.script.ScriptContext

interface Script {

    fun run(context: ScriptContext, content: String)

}