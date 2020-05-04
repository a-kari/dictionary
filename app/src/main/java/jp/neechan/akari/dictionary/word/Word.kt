package jp.neechan.akari.dictionary.word

import jp.neechan.akari.dictionary.word.Definition
import jp.neechan.akari.dictionary.word.Frequency
import jp.neechan.akari.dictionary.word.PartOfSpeech
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