package jp.neechan.akari.dictionary.common

import java.io.Serializable

// todo: Do not use serializable. Pass a "word" string to views (which is id)
// todo: and get the needed word from a repository.
// todo:
// todo: Maybe I should create DTOs for Frequency and PartOfSpeech, too?
data class Word(val word: String,
                val pronunciation: String?,
                val syllables: String?,
                val frequency: Frequency,
                val definitions: LinkedHashMap<PartOfSpeech, List<Definition>>?) : Serializable