package jp.neechan.akari.dictionary.core_api.domain.entities

data class Definition(val id: Long = 0,
                      val wordId: String,
                      val definition: String,
                      val partOfSpeech: PartOfSpeech,
                      val examples: List<String>?,
                      val synonyms: List<String>?,
                      val antonyms: List<String>?)
