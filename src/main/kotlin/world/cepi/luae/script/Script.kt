package world.cepi.luae.script

/**
 * Something that can run content. This content varies depending on the type.
 */
abstract class Script(val content: Any) {

    /**
     * Runs a set of objects with some context.
     *
     * @return If this succeeded or not.
     */
    abstract fun run(scriptContext: ScriptContext): RunResult

}