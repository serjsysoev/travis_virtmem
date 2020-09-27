import com.xenomachina.argparser.ArgParser
import com.xenomachina.argparser.InvalidArgumentException
import com.xenomachina.argparser.default
import java.io.File
import java.io.FileNotFoundException
import java.io.PrintWriter

class ArgumentsParser(parser: ArgParser) {
    val inputFile by parser.storing(
        "-i", "--input",
        help = "Input file with words separated by \\n (required)"
    ) {
        if (!File(this).isFile) throw InvalidArgumentException("$this is not a file")
        if (!File(this).canRead()) throw InvalidArgumentException("$this is not readable")
        File(this).reader()
    }

    val outputFile by parser.storing(
        "-o", "--output",
        help = "Output file"
    ) {
        try {
            File(this).printWriter()
        } catch (ex: FileNotFoundException) {
            throw InvalidArgumentException("$this is not writable")
        }
    }.default(PrintWriter(System.out))
}