package world.cepi.luae

import net.minestom.server.extensions.Extension;

class LuaeExtension : Extension() {

    override fun initialize() {
        logger.info("[LuaeExtension] has been enabled!")
    }

    override fun terminate() {
        logger.info("[LuaeExtension] has been disabled!")
    }

}