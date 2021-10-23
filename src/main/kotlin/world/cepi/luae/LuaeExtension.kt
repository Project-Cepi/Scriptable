package world.cepi.luae

import net.minestom.server.extensions.Extension;
import world.cepi.luae.command.ScriptCommand

class LuaeExtension : Extension() {

    override fun initialize() {

        ScriptCommand.register()

        logger.info("[LuaeEngine] has been enabled!")
    }

    override fun terminate() {

        ScriptCommand.unregister()

        logger.info("[LuaeEngine] has been disabled!")
    }

}