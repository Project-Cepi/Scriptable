package world.cepi.scriptable.script

sealed class RunResult {

    object Success : RunResult()
    class Error(val message: String?) : RunResult()

}