package world.cepi.luae.script

/**
 * Something that can run content. This content varies depending on the type.
 */
interface Script {

    /**
     * Runs a set of objects with some context.
     *
     * @return If this succeeded or not.
     */
    fun run(context: ScriptContext, content: Any): RunResult

}