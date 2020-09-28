@file: JvmName("VirtualMemory")

import com.xenomachina.argparser.ArgParser

fun main(args: Array<String>) {
    ArgParser(args).parseInto(::ArgumentsParser).run {
        val fifoMemory = FIFOMemory(RAMPages, totalPages)
        val lruMemory = LRUMemory(RAMPages, totalPages)
        val optMemory = OPTMemory(RAMPages, totalPages)
        optMemory.constructFutureQueries(input)

        input.forEach {
            output.println("${fifoMemory.getPage(it)} ${lruMemory.getPage(it)} ${optMemory.getPage(it)}")
        }
        output.flush()
        output.close()
    }
}