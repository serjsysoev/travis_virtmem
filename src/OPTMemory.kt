import com.xenomachina.argparser.InvalidArgumentException
import java.util.*

class PageWithIndex(val page: Int, val index: Int) : Comparable<PageWithIndex> {
    override fun compareTo(other: PageWithIndex): Int {
        return this.index - other.index
    }
}

class OPTMemory(val size: Int, val totalPages: Int) {
    private val futureQueries = HashMap<Int, LinkedList<Int>>()
    private val memory = TreeSet<PageWithIndex>()
    private val isPageInMemory = BooleanArray(this.totalPages) { false }
    private var queryID = -1
    private var constructedFutureQueries = false

    fun getPage(page: Int): Int {
        if (!constructedFutureQueries) {
            throw IllegalStateException("You must call constructFutureQueries first!")
        }
        queryID++
        val pageFutureQueries = futureQueries[page]
            ?: run { throw InvalidArgumentException("This page wasn't specified in constructFutureQueries") }
        pageFutureQueries.removeFirst()

        if (isPageInMemory[page]) {
            memory.remove(PageWithIndex(page, queryID))
            val newIndex = if (pageFutureQueries.isEmpty()) Int.MAX_VALUE else pageFutureQueries.first
            memory.add(PageWithIndex(page, newIndex))
            return -1
        }

        val newIndex = if (pageFutureQueries.isEmpty()) Int.MAX_VALUE else pageFutureQueries.first
        memory.add(PageWithIndex(page, newIndex))
        isPageInMemory[page] = true

        return if (memory.size <= this.size) -1 else {
            val pageWithIndex = memory.last()
            memory.remove(pageWithIndex)
            isPageInMemory[pageWithIndex.page] = false
            return pageWithIndex.page
        }
    }

    fun constructFutureQueries(queries: List<Int>) {
        queries.forEachIndexed { index, page ->
            futureQueries[page]?.add(index) ?: run {
                futureQueries[page] = LinkedList(listOf(index))
            }
        }
        constructedFutureQueries = true
    }
}