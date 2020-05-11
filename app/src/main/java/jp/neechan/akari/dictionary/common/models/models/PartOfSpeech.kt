package jp.neechan.akari.dictionary.common.models.models

import jp.neechan.akari.dictionary.R

enum class PartOfSpeech(val stringResource: Int) {

    NOUN(R.string.part_of_speech_noun),
    PRONOUN(R.string.part_of_speech_pronoun),
    ADJECTIVE(R.string.part_of_speech_adjective),
    VERB(R.string.part_of_speech_verb),
    ADVERB(R.string.part_of_speech_adverb),
    PREPOSITION(R.string.part_of_speech_preposition),
    CONJUNCTION(R.string.part_of_speech_conjunction),
    UNKNOWN(R.string.part_of_speech_unknown),
    ALL(R.string.part_of_speech_all)
}