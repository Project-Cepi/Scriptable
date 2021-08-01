package world.cepi.luae.script

sealed class RunResult {

    object Success : RunResult()
    class Error(val message: String?) : RunResult()

}