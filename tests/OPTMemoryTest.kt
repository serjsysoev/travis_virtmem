import com.xenomachina.argparser.InvalidArgumentException
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import java.lang.IllegalStateException

internal class OPTMemoryTest {

    @Test
    fun pageIsAlreadyInTheMemory() {
        val optMemory = OPTMemory(2, 4)
        optMemory.constructFutureQueries(listOf(0, 1, 1))
        optMemory.getPage(0)
        optMemory.getPage(1)
        assertEquals(
            -1,
            optMemory.getPage(1),
            "Expected OPT to return -1 when fed with 0, 1, 1 and size 2"
        )
    }

    @Test
    fun newPageEnoughMemory() {
        val optMemory = OPTMemory(3, 4)
        optMemory.constructFutureQueries(listOf(0, 1, 2))
        optMemory.getPage(0)
        optMemory.getPage(1)
        assertEquals(
            -1,
            optMemory.getPage(2),
            "Expected OPT to return -1 when fed with 0, 1, 2 and size 3"
        )
    }

    @Test
    fun futureVision1() {
        val optMemory = OPTMemory(3, 4)
        optMemory.constructFutureQueries(listOf(0, 1, 2, 3, 0))
        optMemory.getPage(0)
        optMemory.getPage(1)
        optMemory.getPage(2)
        assertNotEquals(
            0,
            optMemory.getPage(3),
            "Expected OPT not to return 0 when fed with 0, 1, 2, 3, future request 0 and size 3"
        )
    }

    @Test
    fun futureVision2() {
        val optMemory = OPTMemory(3, 4)
        optMemory.constructFutureQueries(listOf(0, 1, 0, 2, 3, 0, 1))
        optMemory.getPage(0)
        optMemory.getPage(1)
        optMemory.getPage(0)
        optMemory.getPage(2)
        assertEquals(
            2,
            optMemory.getPage(3),
            "Expected OPT to return 2 when fed with 0, 1, 0, 2, 3, future requests 0, 1 and size 3"
        )
    }

    fun didntConstructFutureQueries() {
        val optMemory = OPTMemory(2, 4)
        assertThrows(IllegalStateException::class.java)  { optMemory.getPage(2) }
    }

    fun constructFutureQueriesTwice() {
        val optMemory = OPTMemory(2, 4)
        assertThrows(IllegalStateException::class.java)  {
            optMemory.constructFutureQueries(listOf(1, 2, 3))
            optMemory.constructFutureQueries(listOf(1, 2, 3))
        }
    }

    fun futureDidntContainRequestedPage() {
        val optMemory = OPTMemory(2, 4)
        assertThrows(InvalidArgumentException::class.java)  {
            optMemory.constructFutureQueries(listOf(0, 1, 3))
            optMemory.getPage(2)
        }
    }

}