package world.cepi.luae

import net.minestom.server.extensions.Extension;
import world.cepi.actions.list.ActionManager
import world.cepi.luae.command.ScriptCommand
import world.cepi.luae.script.action.ScriptAction

class LuaeExtension : Extension() {

    override fun initialize() {

        ActionManager.add(ScriptAction::class)

        ScriptCommand.register()

        logger.info("[LuaeEngine] has been enabled!")
    }

    override fun terminate() {

        ScriptCommand.unregister()

        logger.info("[LuaeEngine] has been disabled!")
    }

}