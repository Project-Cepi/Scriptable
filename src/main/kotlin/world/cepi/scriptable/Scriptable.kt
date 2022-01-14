package world.cepi.scriptable

import net.minestom.server.extensions.Extension;
import world.cepi.actions.list.ActionManager
import world.cepi.kstom.Manager
import world.cepi.kstom.util.log
import world.cepi.scriptable.command.ScriptCommand
import world.cepi.scriptable.script.action.ScriptAction

class Scriptable : Extension() {

    override fun initialize(): LoadStatus {

        ScriptCommand.register()
        ActionManager.add<ScriptAction>()

        log.info("[Scriptable] has been enabled!")

        return LoadStatus.SUCCESS
    }

    override fun terminate() {

//        if (Manager.extension.hasExtension("actions")) {
//            ActionManager.remove(ScriptAction::class)
//        }

        ScriptCommand.unregister()

        log.info("[Scriptable] has been disabled!")
    }

}