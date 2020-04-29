package jp.neechan.akari.dictionary.common

data class Page<T>(
    val content: List<T>,
    val pageNumber: Int,
    val hasNextPage: Boolean
)