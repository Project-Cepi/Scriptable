package world.cepi.scriptable.script.lib

import net.minestom.server.instance.block.Block
import world.cepi.scriptable.script.access.ScriptableExport

class ScriptBlock(val block: Block) {

    @get:ScriptableExport
    val id get() = block.id()

}