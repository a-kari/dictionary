package jp.neechan.akari.dictionary.discover

data class Page<T>(
    val content: List<T>,
    val pageNumber: Int,
    val hasNextPage: Boolean
)