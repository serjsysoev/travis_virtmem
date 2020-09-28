import com.xenomachina.argparser.ArgParser
import com.xenomachina.argparser.InvalidArgumentException
import com.xenomachina.argparser.default
import java.io.File
import java.io.FileNotFoundException
import java.io.PrintWriter

class ArgumentsParser(parser: ArgParser) {
    val input by parser.storing(
        "-i", "--input",
        help = "Input file with words separated by \\n (required)"
    ) {
        if (!File(this).isFile) throw InvalidArgumentException("$this is not a file")
        if (!File(this).canRead()) throw InvalidArgumentException("$this is not readable")
        val lines = File(this).readLines()
        try {
            lines.map { it.toInt() }
        } catch (ex: java.lang.NumberFormatException) {
            throw InvalidArgumentException("Input file contains lines that can't be converted to integer")
        }
    }

    val output by parser.storing(
        "-o", "--output",
        help = "Output file"
    ) {
        try {
            File(this).printWriter()
        } catch (ex: FileNotFoundException) {
            throw InvalidArgumentException("$this is not writable")
        }
    }.default(PrintWriter(System.out))

    val totalPages by parser.storing(
        "-t", "--total-pages",
        help = "Total pages in memory"
    ) {
        try {
            this.toInt()
        } catch (ex: NumberFormatException) {
            throw InvalidArgumentException("$this is not an integer")
        }
    }

    val RAMPages by parser.storing(
        "-r", "--ram-pages",
        help = "Amount of pages that fit into RAM"
    ) {
        try {
            this.toInt()
        } catch (ex: NumberFormatException) {
            throw InvalidArgumentException("$this is not an integer")
        }
    }

}