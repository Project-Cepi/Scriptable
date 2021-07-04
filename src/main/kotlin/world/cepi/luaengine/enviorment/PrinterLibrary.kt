package world.cepi.luaengine.enviorment

import world.cepi.kstom.adventure.asMini
import world.cepi.luae.LuaeContext
import world.cepi.luae.LuaeExternalFunction
import world.cepi.luae.LuaeVarargs
import world.cepi.luae.lib.LuaeLibrary
import world.cepi.luaengine.script.ScriptContext

class PrinterLibrary(val scriptContext: ScriptContext) : LuaeLibrary() {

    val print = LuaeExternalFunction { context, args ->
        for (arg in args) {
            scriptContext.player.sendMessage("$arg".asMini())
        }
        context.returnFunction() // Don't forget this.
    }

    init {

        table.set("print", print)

    }

}