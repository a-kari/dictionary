package jp.neechan.akari.dictionary.word

import jp.neechan.akari.dictionary.word.DefinitionDTO
import jp.neechan.akari.dictionary.word.Frequency

data class WordDTO(val word: String,
                   val pronunciation: String?,
                   val syllables: List<String>?,
                   val frequency: Frequency,
                   val definitions: List<DefinitionDTO>?)