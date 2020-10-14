package unitTests

import LRUMemory
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class LRUMemoryTest {

    @Test
    fun pageIsAlreadyInTheMemory() {
        val lruMemory = LRUMemory(2, 4)
        lruMemory.getPage(0)
        lruMemory.getPage(1)
        assertEquals(
            -1,
            lruMemory.getPage(1),
            "Expected LRU to return -1 when fed with 0, 1, 1 and size 2"
        )
    }

    @Test
    fun newPageEnoughMemory() {
        val lruMemory = LRUMemory(3, 4)
        lruMemory.getPage(0)
        lruMemory.getPage(1)
        assertEquals(
            -1,
            lruMemory.getPage(2),
            "Expected LRU to return -1 when fed with 0, 1, 2 and size 3"
        )
    }

    @Test
    fun memoryOverflow1() {
        val lruMemory = LRUMemory(2, 4)
        lruMemory.getPage(0)
        lruMemory.getPage(1)
        assertEquals(
            0,
            lruMemory.getPage(2),
            "Expected LRU to unload 0 when fed with 0, 1, 2 and size 2"
        )
    }

    @Test
    fun memoryOverflow2() {
        val lruMemory = LRUMemory(2, 4)
        lruMemory.getPage(0)
        lruMemory.getPage(1)
        lruMemory.getPage(0)
        assertEquals(
            1,
            lruMemory.getPage(2),
            "Expected LRU to unload 1 when fed with 0, 1, 0, 2 and size 2"
        )
    }

    @Test
    fun memoryOverflowOrder() {
        val lruMemory = LRUMemory(3, 6)
        lruMemory.getPage(0)
        lruMemory.getPage(1)
        lruMemory.getPage(2)
        lruMemory.getPage(0)
        assertEquals(
            1,
            lruMemory.getPage(3),
            "Expected LRU to unload 1 when fed with 0, 1, 2, 0, 3 and size 3"
        )
        assertEquals(
            2,
            lruMemory.getPage(4),
            "Expected LRU to unload 2 when fed with 0, 1, 2, 0, 3, 4 and size 3"
        )
        assertEquals(
            0,
            lruMemory.getPage(5),
            "Expected LRU to unload 0 when fed with 0, 1, 2, 0, 3, 4, 5 and size 3"
        )
    }
}