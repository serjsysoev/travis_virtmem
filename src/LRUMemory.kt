import kotlin.collections.LinkedHashSet

class LRUMemory(val size: Int, val totalPages: Int) {
    private val memory = LinkedHashSet<Int>() // https://stackoverflow.com/questions/2319086/a-queue-that-ensure-uniqueness-of-the-elements
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