package jp.neechan.akari.dictionary.word

import androidx.annotation.StringRes
import jp.neechan.akari.dictionary.R
import jp.neechan.akari.dictionary.word.PartOfSpeech.*

enum class PartOfSpeech {

    NOUN,
    PRONOUN,
    ADJECTIVE,
    VERB,
    ADVERB,
    PREPOSITION,
    CONJUNCTION,
    INTERJECTION,
    UNKNOWN;

    companion object {
        fun valueOf(partOfSpeechString: String?): PartOfSpeech {
            return values().firstOrNull { it.name.equals(partOfSpeechString, true) } ?: UNKNOWN
        }
    }
}

// todo: The extension should be in UI layer when I apply Clean Architecture.
@StringRes
fun PartOfSpeech?.getStringResource(): Int {
    return when (this) {
        NOUN -> R.string.part_of_speech_noun
        PRONOUN -> R.string.part_of_speech_pronoun
        ADJECTIVE -> R.string.part_of_speech_adjective
        VERB -> R.string.part_of_speech_verb
        ADVERB -> R.string.part_of_speech_adverb
        PREPOSITION -> R.string.part_of_speech_preposition
        CONJUNCTION -> R.string.part_of_speech_conjunction
        INTERJECTION -> R.string.part_of_speech_interjection
        UNKNOWN -> R.string.part_of_speech_unknown
        null -> R.string.part_of_speech_all
    }
}