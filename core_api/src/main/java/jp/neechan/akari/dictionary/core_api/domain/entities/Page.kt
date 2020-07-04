package jp.neechan.akari.dictionary.core_api.domain.entities

/**
 * Represents a page of T items. Counts pages starting from 1.
 *
 * @param T is a type of content items.
 */
data class Page<T>(
    val content: List<T>,
    val pageNumber: Int,
    val hasNextPage: Boolean
) : Iterable<T> {

    constructor(content: List<T>, pageNumber: Int, limit: Int, total: Int) :
        this(content, pageNumber, total > pageNumber * limit)

    fun isEmpty(): Boolean {
        return content.isEmpty()
    }

    fun isNotEmpty(): Boolean {
        return !isEmpty()
    }

    operator fun get(index: Int): T {
        return content[index]
    }

    override fun iterator(): Iterator<T> {
        return object : Iterator<T> {
            private var currentIndex = 0

            override fun hasNext(): Boolean {
                return currentIndex < content.size
            }

            override fun next(): T {
                return content[currentIndex++]
            }
        }
    }
}
