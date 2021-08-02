package world.cepi.luae.script

import org.graalvm.polyglot.HostAccess
import org.graalvm.polyglot.Value

fun interface Promisable {

    @HostAccess.Export
    fun onPromiseCreation(onResolve: Value, onReject: Value)

}