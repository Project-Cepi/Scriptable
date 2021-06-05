package world.cepi.luae.script

import net.minestom.server.command.CommandSender
import org.graalvm.polyglot.HostAccess
import world.cepi.kstom.Manager

object Executor {

    @HostAccess.Export
    fun run(command: String, who: CommandSender, vararg permissions: String) {

        Manager.command.execute(who, command)

    }

}