package jp.neechan.akari.dictionary.common.models.models

data class Definition(val id: Long,
                      val wordId: String,
                      val definition: String,
                      val partOfSpeech: PartOfSpeech,
                      val examples: String?,
                      val synonyms: String?,
                      val antonyms: String?)