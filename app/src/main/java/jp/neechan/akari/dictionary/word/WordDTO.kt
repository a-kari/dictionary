package jp.neechan.akari.dictionary.word

data class WordDTO(val word: String,
                   val pronunciation: String?,
                   val syllables: List<String>?,
                   val frequency: Frequency,
                   val definitions: List<DefinitionDTO>?)