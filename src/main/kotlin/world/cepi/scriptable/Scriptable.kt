package world.cepi.scriptable

import net.minestom.server.extensions.Extension;
import world.cepi.actions.list.ActionManager
import world.cepi.scriptable.command.ScriptCommand
import world.cepi.scriptable.script.action.ScriptAction

class Scriptable : Extension() {

    override fun initialize() {

        ActionManager.add(ScriptAction::class)

        ScriptCommand.register()

        logger.info("[Scriptable] has been enabled!")
    }

    override fun terminate() {

        ActionManager.remove(ScriptAction::class)

        ScriptCommand.unregister()

        logger.info("[Scriptable] has been disabled!")
    }

}