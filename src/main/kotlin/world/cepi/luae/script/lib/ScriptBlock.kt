package world.cepi.luae.script.lib

import net.minestom.server.instance.block.Block
import org.graalvm.polyglot.HostAccess
import world.cepi.luae.script.access.ScriptableExport

class ScriptBlock(val block: Block) {

    @get:ScriptableExport
    val id get() = block.id()

}