package world.cepi.luae.script.lib

import world.cepi.kstom.adventure.asMini
import world.cepi.luae.LuaeExternalFunction
import world.cepi.luae.lib.LuaeLibrary
import world.cepi.luae.script.ScriptContext

class PrinterLibrary(val scriptContext: ScriptContext) : LuaeLibrary() {

    private val print = LuaeExternalFunction { context, args ->
        for (arg in args) {
            scriptContext.sender?.sendMessage("$arg".asMini())
        }
        context.returnFunction() // Don't forget this.
    }

    init {

        table.set("print", print)

    }

}