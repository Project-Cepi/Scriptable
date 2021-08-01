package world.cepi.luae.script

import org.graalvm.polyglot.Value

fun interface Thenable {

    fun then(onResolve: Value, onReject: Value)

}