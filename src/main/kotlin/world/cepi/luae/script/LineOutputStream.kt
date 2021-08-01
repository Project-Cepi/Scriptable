package world.cepi.luae.script

import java.io.IOException
import java.io.OutputStream
import java.nio.charset.StandardCharsets
import java.util.function.Consumer

/**
 * Represents an output stream that reads line by line.
 *
 * Modified from [https://stackoverflow.com/questions/58468441/java-outputstream-reading-lines-of-strings]
 */
class LineReadingOutputStream(val consumer: Consumer<String>) : OutputStream() {
    private val stringBuilder = StringBuilder()
    private var lastCR = false

    @Throws(IOException::class)
    override fun write(b: Int) {
        write(byteArrayOf(b.toByte()))
    }

    override fun write(b: ByteArray, start: Int, len: Int) {
        var start = start
        require(len >= 0)
        val end = start + len
        if (start < 0 || start > b.size || end < 0 || end > b.size) {
            throw IndexOutOfBoundsException()
        }
        if (lastCR && start < end && b[start] == LF) {
            start++
            lastCR = false
        } else if (start < end) {
            lastCR = b[end - 1] == CR
        }
        var base = start
        var i = start
        while (i < end) {
            if (b[i] == LF || b[i] == CR) {
                val chunk = asString(b, base, i)
                stringBuilder.append(chunk)
                consume()
            }
            if (b[i] == LF) {
                base = i + 1
            } else if (b[i] == CR) {
                if (i < end - 1 && b[i + 1] == LF) {
                    base = i + 2
                    i++
                } else {
                    base = i + 1
                }
            }
            i++
        }
        val chunk = asString(b, base, end)
        stringBuilder.append(chunk)
    }

    override fun close() {
        if (stringBuilder.isNotEmpty()) {
            consume()
        }
    }

    private fun consume() {
        consumer.accept(stringBuilder.toString())
        stringBuilder.delete(0, Int.MAX_VALUE)
    }

    companion object {
        private const val CR = '\r'.code.toByte()
        private const val LF = '\n'.code.toByte()
        private fun asString(b: ByteArray, start: Int, end: Int): String {
            require(start <= end)
            if (start == end) {
                return ""
            }
            val copy = b.copyOfRange(start, end)
            return String(copy, StandardCharsets.UTF_8)
        }
    }

}