package world.cepi.luae.script.objects.util

import org.graalvm.polyglot.HostAccess
import world.cepi.luae.script.Script
import java.util.concurrent.CompletableFuture

object ScriptHelpers {

    @HostAccess.Export
    fun sleep(time: Long) = Script.wrapPromise(CompletableFuture.runAsync {
        println("a")
        Thread.sleep(time)
    })

}