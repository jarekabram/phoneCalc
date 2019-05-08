package sample

class Stack<T> : MutableIterable<T> {
    val elements: MutableList<T> = mutableListOf()

    fun isEmpty(): Boolean {
        return elements.count() == 0
    }
    fun size() = elements.size
    fun push(item: T) = elements.add(item)
    fun pop() : T? {
        val item = elements.lastOrNull()
        if (!isEmpty()){
            elements.removeAt(elements.size -1)
        }
        return item
    }
    fun peek() : T? = elements.lastOrNull()
    fun replaceTop(input: T) {
        var lastIndex = elements.lastIndex
        elements[lastIndex] = input
    }
    override fun toString(): String = elements.toString()

    override fun iterator(): MutableIterator<T> {
        return StackIterator()
    }

    inner class StackIterator : MutableIterator<T> {
        var current: Int = 0

        override fun remove() {
            elements.removeAt(current)
        }
        override fun hasNext(): Boolean {
            return current < elements.size
        }
        override fun next(): T {
            val temp = elements[current]
            if(current >= elements.size) {
                throw IndexOutOfBoundsException("Too large index")
            }
            else {
                current++
            }
            return temp
        }
    }
}