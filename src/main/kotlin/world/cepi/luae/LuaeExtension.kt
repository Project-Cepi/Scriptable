package world.cepi.luae

import net.minestom.server.MinecraftServer
import net.minestom.server.extensions.Extension;
import world.cepi.luae.command.ScriptCommand

class LuaeExtension : Extension() {

    override fun initialize() {

        MinecraftServer.getCommandManager().register(ScriptCommand)

        logger.info("[LuaeExtension] has been enabled!")
    }

    override fun terminate() {

        MinecraftServer.getCommandManager().unregister(ScriptCommand)

        logger.info("[LuaeExtension] has been disabled!")
    }

}