package world.cepi.luae.script

import org.graalvm.polyglot.HostAccess
import org.graalvm.polyglot.Value

fun interface Thenable {

    @HostAccess.Export
    fun then(onResolve: Value, onReject: Value)

}