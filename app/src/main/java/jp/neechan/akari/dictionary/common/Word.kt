package jp.neechan.akari.dictionary.common

data class Word(val word: String,
                val pronunciation: String,
                val syllables: List<String>,
                val definitions: List<Definition>,
                val frequency: Double)