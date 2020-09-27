@file: JvmName("VirtualMemory")

import com.xenomachina.argparser.ArgParser

fun main(args: Array<String>) {
    ArgParser(args).parseInto(::ArgumentsParser).run {
        println("Hello World!")
    }
}