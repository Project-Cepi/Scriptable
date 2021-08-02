package world.cepi.luae.script

import com.oracle.truffle.js.runtime.JSContextOptions
import kotlinx.serialization.Serializable
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.text.format.TextDecoration
import net.minestom.server.command.CommandSender
import net.minestom.server.entity.Player
import net.minestom.server.item.ItemStack
import net.minestom.server.item.Material
import net.minestom.server.tag.Tag
import org.graalvm.polyglot.Context
import org.graalvm.polyglot.management.ExecutionEvent
import world.cepi.kstom.item.item
import world.cepi.kstom.item.withMeta
import org.graalvm.polyglot.management.ExecutionListener
import world.cepi.luae.script.objects.util.ScriptHelpers

import java.util.concurrent.CompletableFuture

/**
 * Something that can run code w/ context.
 */
@Serializable
class Script(val content: String = "") {

    companion object {
        const val key = "luae-script"
        const val currentLanguage = "js"

        fun <T> wrapPromise(javaFuture: CompletableFuture<T>) {
            Promisable { onResolve, onReject ->
                javaFuture.whenComplete { result: T?, ex: Throwable? ->
                    if (ex == null)
                        onResolve.execute(result)
                    else
                        onReject.execute(ex)
                }
            }
        }

    }

    /**
     * Runs a set of objects with some context.
     *
     * @return If this succeeded or not.
     */
    fun run(scriptContext: ScriptContext): RunResult {

        val oldCl = Thread.currentThread().contextClassLoader
        Thread.currentThread().contextClassLoader = javaClass.classLoader

        val consoleStream = LineReadingOutputStream { scriptContext.sender?.sendMessage(it) }

        Context
            .newBuilder(currentLanguage)
            .allowExperimentalOptions(true)
            .option(JSContextOptions.INTEROP_COMPLETE_PROMISES_NAME, "true")
            .option(JSContextOptions.CONSOLE_NAME, "true")
            .out(consoleStream)
            .build()
            .use { context ->

                val listener = ExecutionListener.newBuilder()
                    .onEnter { e: ExecutionEvent ->
                        // TODO stop running after X amount of iterations.
                    }
                    .statements(true)
                    .attach(context.engine)

                context.getBindings(currentLanguage).apply {
                    putMember("executor", Executor)
                    putMember("context", scriptContext)
                    putMember("script", ScriptHelpers)
                }

                try {
                    context.eval(currentLanguage, content)
                } catch (e: Exception) {
                    listener.close()
                    Thread.currentThread().contextClassLoader = oldCl
                    return RunResult.Error(e.message)
                }
                listener.close()
        }

        Thread.currentThread().contextClassLoader = oldCl

        return RunResult.Success
    }

    fun runAsSender(sender: CommandSender) {
        val runResult = run(ScriptContext(
            sender,
            (sender as? Player)?.instance,
            (sender as? Player)?.position
        ))

        when (runResult) {
            is RunResult.Success -> sender.sendMessage(Component.text("Success!", NamedTextColor.GREEN))
            is RunResult.Error -> {
                Component.text(runResult.message ?: "An internal error occurred while running this script", NamedTextColor.RED)
            }
        }
    }

    fun asItem(): ItemStack = item(Material.PAPER) {
        displayName(Component.text("Script", NamedTextColor.GREEN).decoration(TextDecoration.ITALIC, false))

        withMeta {
            this.setTag(Tag.String(key), content)
        }
    }


}

val ItemStack.scriptString: String?
    get() = this.meta.getTag(Tag.String(Script.key))