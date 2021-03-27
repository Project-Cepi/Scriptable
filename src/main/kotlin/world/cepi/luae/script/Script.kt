package world.cepi.luae.script

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import net.minestom.server.data.DataImpl
import net.minestom.server.item.ItemStack
import net.minestom.server.item.Material
import org.luaj.vm2.lib.jse.JsePlatform

/**
 * Something that can run code w/ context.
 */
class Script(val content: String = "") {

    companion object {
        const val key = "luae-script"
    }

    /**
     * Runs a set of objects with some context.
     *
     * @return If this succeeded or not.
     */
    fun run(scriptContext: ScriptContext): RunResult {
        val globals = JsePlatform.standardGlobals()
        val chunk = globals.load(content)
        chunk.call()

        return RunResult.SUCCESS
    }

    fun asItem(): ItemStack {

        val scriptItem = ItemStack(Material.PAPER, 1)

        scriptItem.displayName = Component.text("Script", NamedTextColor.GREEN)

        val data = DataImpl()

        data.set(key, this)

        return scriptItem
    }

}