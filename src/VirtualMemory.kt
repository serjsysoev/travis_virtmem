@file: JvmName("VirtualMemory")

import com.xenomachina.argparser.ArgParser
import com.xenomachina.argparser.InvalidArgumentException

fun main(args: Array<String>) {
    ArgParser(args).parseInto(::ArgumentsParser).run {
        if (tests.isEmpty() && input.isEmpty()) {
            throw InvalidArgumentException("No input values present. Please refer to --help")
        }

        val pagesQueue = if (tests.isEmpty()) input else tests

        val fifoMemory = FIFOMemory(RAMPages, totalPages)
        val lruMemory = LRUMemory(RAMPages, totalPages)
        val optMemory = OPTMemory(RAMPages, totalPages)
        optMemory.constructFutureQueries(pagesQueue)

        pagesQueue.forEach {
            output.println("${fifoMemory.getPage(it)} ${lruMemory.getPage(it)} ${optMemory.getPage(it)}")
        }
        output.flush()
        output.close()
    }
}