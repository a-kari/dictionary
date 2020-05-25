package jp.neechan.akari.dictionary.base.domain.entities

data class Page<T>(
    val content: List<T>,
    val pageNumber: Int,
    val hasNextPage: Boolean
)