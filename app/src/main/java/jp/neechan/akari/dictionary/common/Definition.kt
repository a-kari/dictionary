package jp.neechan.akari.dictionary.common

data class Definition(val definition: String,
                      val partOfSpeech: PartOfSpeech,
                      val synonyms: List<String>,
                      val antonyms: List<String>,
                      val examples: List<String>)