import com.xenomachina.argparser.InvalidArgumentException
import java.util.*

class PageWithIndex(val page: Int, val index: Int) : Comparable<PageWithIndex> {
    override fun compareTo(other: PageWithIndex): Int {
        return this.index - other.index
    }
}

/**
 * Implementation of FIFO virtual memory model, constructor needs [size] of memory in pages
 * and [total pages][totalPages] count. Needs to be initialized with future requests through constructFutureQueries!
 */
class OPTMemory(val size: Int, val totalPages: Int) {
    private val futureQueries = HashMap<Int, LinkedList<Int>>()
    private val memory = TreeSet<PageWithIndex>()
    private val isPageInMemory = BooleanArray(this.totalPages) { false }
    private var queryID = -1
    private var constructedFutureQueries = false

    /**
     * removes page from memory
     */
    private fun removePage(pageWithIndex: PageWithIndex): PageWithIndex {
        memory.remove(pageWithIndex)
        isPageInMemory[pageWithIndex.page] = false
        return pageWithIndex
    }

    /**
     * adds page to memory
     */
    private fun addPage(pageWithIndex: PageWithIndex): PageWithIndex {
        memory.add(pageWithIndex)
        isPageInMemory[pageWithIndex.page] = true
        return pageWithIndex
    }

    /**
     * Returns -1 if no pages were unloaded while getting [page]. otherwise unloaded page number.
     */
    fun getPage(page: Int): Int {
        if (!constructedFutureQueries) throw IllegalStateException("You must call constructFutureQueries first!")

        queryID++
        val pageFutureQueries = futureQueries[page]
            ?: run { throw InvalidArgumentException("This page wasn't specified in constructFutureQueries") }
        pageFutureQueries.removeFirst() // this query is not future anymore
        val newIndex = if (pageFutureQueries.isEmpty()) Int.MAX_VALUE else pageFutureQueries.first

        if (isPageInMemory[page]) {
            removePage(PageWithIndex(page, queryID))
            addPage(PageWithIndex(page, newIndex)) // updating page next query index since queryID already happened
            return -1
        }

        val result = if (memory.size == this.size) removePage(memory.last()).page else -1
        addPage(PageWithIndex(page, newIndex))
        return result
    }

    /**
     * Initializes OPTMemory with list of future values that would be fed into getPage function
     */
    fun constructFutureQueries(queries: List<Int>) {
        if (constructedFutureQueries) throw IllegalStateException("You already called constructFutureQueries!")

        queries.forEachIndexed { index, page ->
            futureQueries[page]?.add(index) ?: run {
                futureQueries[page] = LinkedList(listOf(index))
            }
        }

        constructedFutureQueries = true
    }
}