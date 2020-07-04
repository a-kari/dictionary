package jp.neechan.akari.dictionary.core_api.domain.entities

import java.util.Date

data class Word(
    val word: String,
    val pronunciation: String?,
    val syllables: List<String>?,
    val frequency: Frequency,
    var definitions: List<Definition>?,
    var saveDate: Date? = null
)
