package jp.neechan.akari.dictionary.common.models.models

data class Definition(val definition: String,
                      val partOfSpeech: PartOfSpeech,
                      val examples: String?,
                      val synonyms: String?,
                      val antonyms: String?)