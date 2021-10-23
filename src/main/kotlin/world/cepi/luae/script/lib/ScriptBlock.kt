package world.cepi.luae.script.lib

import net.minestom.server.instance.block.Block
import org.graalvm.polyglot.HostAccess

class ScriptBlock(val block: Block) {

    @get:HostAccess.Export
    val id get() = block.id()

}