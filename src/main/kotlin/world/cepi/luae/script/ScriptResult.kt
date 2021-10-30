package world.cepi.luae.script

import java.time.Instant
import java.util.concurrent.atomic.AtomicInteger

class ScriptResult(val created: Instant, val steps: AtomicInteger, val thread: Thread) {

    fun interrupt() = thread.interrupt()

}