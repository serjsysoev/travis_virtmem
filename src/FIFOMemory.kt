import java.util.*


/**
 * Implementation of FIFO virtual memory model, constructor needs [size] of memory in pages
 * and [total pages][totalPages] count
 */
class FIFOMemory(val size: Int, val totalPages: Int) {
    private val isPageInMemory = BooleanArray(this.totalPages) { false }
    private val memory = LinkedList<Int>()

    /**
     * Returns -1 if no pages were unloaded while getting [page]. otherwise unloaded page number.
     */
    fun getPage(page: Int): Int {
        if (isPageInMemory[page]) return -1

        memory.add(page)
        isPageInMemory[page] = true

        return if (memory.size <= this.size) -1 else {
            val removedPage = memory.removeFirst()
            isPageInMemory[removedPage] = false
            return removedPage
        }
    }
}