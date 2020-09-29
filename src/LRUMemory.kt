import kotlin.collections.LinkedHashSet

/**
 * Implementation of LRU virtual memory model, constructor needs [size] of memory in pages
 * and [total pages][totalPages] count
 */
class LRUMemory(val size: Int, val totalPages: Int) {
    private val memory = LinkedHashSet<Int>() // https://stackoverflow.com/questions/2319086/a-queue-that-ensure-uniqueness-of-the-elements

    /**
     * Returns -1 if no pages were unloaded while getting [page]. otherwise unloaded page number.
     */
    fun getPage(page: Int): Int {
        if (memory.contains(page)) {
            memory.remove(page)
            memory.add(page) // this way we move page to the end of our queue
            return -1
        }

        memory.add(page)

        return if (memory.size <= this.size) -1 else {
            val iterator = memory.iterator()
            val result = iterator.next()
            iterator.remove() // remove first element in queue
            return result
        }
    }
}