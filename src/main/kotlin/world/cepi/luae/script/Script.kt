package world.cepi.luae.script

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import net.minestom.server.data.DataImpl
import net.minestom.server.item.ItemStack
import net.minestom.server.item.Material
import org.luaj.vm2.lib.jse.JsePlatform
import java.io.ByteArrayOutputStream
import java.io.PrintStream
import java.nio.charset.StandardCharsets


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

        val byteArrayOutputStream = ByteArrayOutputStream()
        val printStream = PrintStream(byteArrayOutputStream, true, "utf-8")

        val globals = JsePlatform.standardGlobals()

        globals.STDOUT = printStream

        val chunk = globals.load(content)
        chunk.call()

        val output = String(byteArrayOutputStream.toByteArray(), StandardCharsets.UTF_8)

        scriptContext.player?.sendMessage(Component.text(output))

        return RunResult.SUCCESS
    }

    fun asItem(): ItemStack {

        val scriptItem = ItemStack(Material.PAPER, 1)

        scriptItem.displayName = Component.text("Script", NamedTextColor.GREEN)

        val data = DataImpl()

        data.set(key, this)

        scriptItem.data = data

        return scriptItem
    }

}