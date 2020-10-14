package integrationTests

import main
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.PrintStream


internal class IntegrationTests {
    fun test(test: Int, n: Int, m: Int) {
        val originalOut = System.out
        val outContent = ByteArrayOutputStream()
        System.setOut(PrintStream(outContent))
        main(arrayOf("-i", "data/input${test}_${n}_${m}", "-n", "$n", "-m", "$m"))
        val expected = File("data/output${test}_${n}_${m}").readText()
        assertEquals(expected, outContent.toString())
        System.setOut(originalOut)
    }

    @Test
    fun test1_4_2() {
        test(1, 4, 2)
    }

    @Test
    fun test2_4_2() {
        test(2, 4, 2)
    }

    @Test
    fun test3_1_1() {
        test(3, 1, 1)
    }

    @Test
    fun test4_101_50() {
        test(4, 101, 50)
    }

    @Test
    fun test5_100_20() {
        test(5, 100, 20)
    }
}