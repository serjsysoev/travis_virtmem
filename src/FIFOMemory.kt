import java.util.*
import kotlin.collections.ArrayList

class FIFOMemory(val size: Int, val totalPages: Int) {
    private val isPageInMemory = ArrayList<Boolean>()
    private val memory = LinkedList<Int>()
    fun getPage(page: Int): Int {
        if (isPageInMemory[page]) return -1

        memory.add(page)
        isPageInMemory[page] = true

        return if (memory.size <= this.size) -1 else memory.removeFirst()
    }
}