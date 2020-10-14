import com.xenomachina.argparser.ArgParser
import com.xenomachina.argparser.InvalidArgumentException
import com.xenomachina.argparser.default
import java.io.File
import java.io.FileNotFoundException
import java.io.IOException
import java.io.PrintWriter
import kotlin.random.Random

class ArgumentsParser(parser: ArgParser) {
    val input by parser.storing(
        "-i", "--input",
        help = "Input file with words separated by \\n (required)"
    ) {
        try {
            File(this).readLines().map { it.toInt() }
        } catch (ex: NumberFormatException) {
            throw InvalidArgumentException("Input file contains lines that can't be converted to integer")
        } catch (ex: IOException) {
            throw InvalidArgumentException("Error reading input file")
        }
    }.default { ArrayList<Int>() }

    val output by parser.storing(
        "-o", "--output",
        help = "Output file"
    ) {
        try {
            File(this).printWriter()
        } catch (ex: FileNotFoundException) {
            throw InvalidArgumentException("Output file is not writable")
        }
    }.default(PrintWriter(System.out))

    val totalPages by parser.storing(
        "-n", "--total-pages", // -n because it's called N in README.md
        help = "Total pages in memory"
    ) {
        try {
            this.toInt()
        } catch (ex: NumberFormatException) {
            throw InvalidArgumentException("Total pages can not be converted to integer")
        }
    }

    val RAMPages by parser.storing(
        "-m", "--ram-pages", // -m because it's called M in README.md
        help = "Amount of pages that fit into RAM"
    ) {
        try {
            this.toInt()
        } catch (ex: NumberFormatException) {
            throw InvalidArgumentException("Ram pages can not be converted to integer")
        }
    }

    val tests by parser.storing(
        "-t", "--test-size",
        help = "Amount of lines to generate(used instead of input file)"
    ) {
        val tests = try {
            List(this.toInt()) { Random.nextInt(0, totalPages) }
        } catch (ex: NumberFormatException) {
            throw InvalidArgumentException("Test size can not be converted to integer")
        }
        println(tests)
        tests
    }.default { ArrayList<Int>() }

    val plot by parser.flagging(
        "-p", "--plot",
        help = "Plot results"
    )

    val debug by parser.flagging(
        "-d", "--debug",
        help = "Prints exception with stacktrace"
    )
}