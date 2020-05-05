package jp.neechan.akari.dictionary.common.models.models

data class Page<T>(
    val content: List<T>,
    val pageNumber: Int,
    val hasNextPage: Boolean
)