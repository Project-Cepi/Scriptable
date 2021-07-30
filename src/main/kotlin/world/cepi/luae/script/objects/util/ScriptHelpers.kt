package world.cepi.luae.script.objects.util

import org.graalvm.polyglot.HostAccess
import java.util.concurrent.CompletableFuture
import java.util.concurrent.Executors

object ScriptHelpers {

    @HostAccess.Export
    fun sleep(time: Long): CompletableFuture<Void> {
        val completableFuture = CompletableFuture<Void>()

        Executors.newCachedThreadPool().submit {
            Thread.sleep(time)
            completableFuture.complete(null)
        }

        return completableFuture
    }

}