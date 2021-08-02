package world.cepi.luae.script.objects.util

import org.graalvm.polyglot.HostAccess
import world.cepi.luae.script.Promisable
import java.util.concurrent.CompletableFuture

object ScriptHelpers {

    @HostAccess.Export
    fun sleep(time: Long) = Promisable { resolve, _ ->
        CompletableFuture.runAsync {
            Thread.sleep(time)
            resolve.executeVoid()
        }
    }

}