import com.xenomachina.argparser.ArgParser
import com.xenomachina.argparser.InvalidArgumentException
import com.xenomachina.argparser.default
import java.io.File
import java.io.FileNotFoundException
import java.io.PrintWriter
import kotlin.random.Random

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
    }.default { ArrayList<Int>() }

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
        "-n", "--total-pages", // -n because it's called N in README.md
        help = "Total pages in memory"
    ) {
        try {
            this.toInt()
        } catch (ex: NumberFormatException) {
            throw InvalidArgumentException("$this is not an integer")
        }
    }

    val RAMPages by parser.storing(
        "-m", "--ram-pages", // -m because it's called M in README.md
        help = "Amount of pages that fit into RAM"
    ) {
        try {
            this.toInt()
        } catch (ex: NumberFormatException) {
            throw InvalidArgumentException("$this is not an integer")
        }
    }

    val tests by parser.storing(
        "-t", "--test-size",
        help = "Amount of lines to generate(used instead of input file)"
    ) {
        try {
             val tests = List(this.toInt()) { Random.nextInt(0, totalPages) }
            println(tests)
            tests
        } catch (ex: NumberFormatException) {
            throw InvalidArgumentException("$this is not an integer")
        }
    }.default { ArrayList<Int>() }

    val plot by parser.flagging(
        "-p", "--plot",
        help = "Plot results"
    )
}