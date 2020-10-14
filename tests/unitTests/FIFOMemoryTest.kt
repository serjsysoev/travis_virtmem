package unitTests

import FIFOMemory
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class FIFOMemoryTest {
    @Test
    fun memoryOverflow() {
        val fifoMemory = FIFOMemory(2, 4)
        fifoMemory.getPage(0)
        fifoMemory.getPage(1)
        assertEquals(
            0,
            fifoMemory.getPage(2),
            "Expected FIFO to unload 0 when fed with 0, 1, 2 and size 2"
        )
    }

    @Test
    fun pageIsAlreadyInTheMemory() {
        val fifoMemory = FIFOMemory(2, 4)
        fifoMemory.getPage(0)
        fifoMemory.getPage(1)
        assertEquals(
            -1,
            fifoMemory.getPage(1),
            "Expected FIFO to return -1 when fed with 0, 1, 1 and size 2"
        )
    }

    @Test
    fun newPageEnoughMemory() {
        val fifoMemory = FIFOMemory(3, 4)
        fifoMemory.getPage(0)
        fifoMemory.getPage(1)
        assertEquals(
            -1,
            fifoMemory.getPage(2),
            "Expected FIFO to return -1 when fed with 0, 1, 2 and size 3"
        )
    }
}