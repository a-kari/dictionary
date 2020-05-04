package jp.neechan.akari.dictionary.word

data class DefinitionDTO(val definition: String,
                         val partOfSpeech: PartOfSpeech,
                         val examples: List<String>?,
                         val synonyms: List<String>?,
                         val antonyms: List<String>?)
