package world.cepi.luae.script

import org.graalvm.polyglot.HostAccess
import world.cepi.kstom.Manager
import world.cepi.luae.wrapper.ScriptPlayer

object Executor {

    @HostAccess.Export
    fun run(command: String, who: ScriptPlayer, vararg permissions: String) {

        Manager.command.execute(who.player, command)

    }

}