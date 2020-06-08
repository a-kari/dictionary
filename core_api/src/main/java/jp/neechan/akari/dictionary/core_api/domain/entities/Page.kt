package jp.neechan.akari.dictionary.core_api.domain.entities

/**
 * Represents a page of T items. Counts pages starting from 1.
 *
 * @param T is a type of content items.
 */
data class Page<T>(val content: List<T>,
                   val pageNumber: Int,
                   val hasNextPage: Boolean) {

    constructor(content: List<T>, pageNumber: Int, limit: Int, total: Int)
        : this(content, pageNumber, total > pageNumber * limit)

    fun isEmpty(): Boolean {
        return content.isEmpty()
    }

    fun isNotEmpty(): Boolean {
        return !isEmpty()
    }
}