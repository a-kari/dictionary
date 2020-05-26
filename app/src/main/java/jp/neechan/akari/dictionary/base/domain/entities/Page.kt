package jp.neechan.akari.dictionary.base.domain.entities

data class Page<T>(val content: List<T>,
                   val pageNumber: Int,
                   val hasNextPage: Boolean) {

    fun isEmpty(): Boolean {
        return content.isEmpty()
    }

    fun isNotEmpty(): Boolean {
        return !isEmpty()
    }
}