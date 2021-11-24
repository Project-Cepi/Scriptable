package world.cepi.scriptable

import net.minestom.server.extensions.Extension;
import world.cepi.actions.list.ActionManager
import world.cepi.kstom.Manager
import world.cepi.scriptable.command.ScriptCommand
import world.cepi.scriptable.script.action.ScriptAction

class Scriptable : Extension() {

    override fun initialize() {

        ScriptCommand.register()

        logger.info("[Scriptable] has been enabled!")
    }
//
//    override fun postInitialize() {
//        if (Manager.extension.hasExtension("actions")) {
//            ActionManager.add(ScriptAction::class)
//        }
//    }

    override fun terminate() {

//        if (Manager.extension.hasExtension("actions")) {
//            ActionManager.remove(ScriptAction::class)
//        }

        ScriptCommand.unregister()

        logger.info("[Scriptable] has been disabled!")
    }

}