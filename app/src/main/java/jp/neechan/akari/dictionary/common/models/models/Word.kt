package jp.neechan.akari.dictionary.common.models.models

import java.util.*

data class Word(val word: String,
                val pronunciation: String?,
                val syllables: String?,
                val frequency: Frequency,
                val definitions: LinkedHashMap<PartOfSpeech, List<Definition>>?,
                val saveDate: Date?,
                val isSaved: Boolean = saveDate != null)