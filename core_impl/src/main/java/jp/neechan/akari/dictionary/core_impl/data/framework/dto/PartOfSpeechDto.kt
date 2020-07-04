package jp.neechan.akari.dictionary.core_impl.data.framework.dto

internal enum class PartOfSpeechDto {

    NOUN,
    PRONOUN,
    ADJECTIVE,
    VERB,
    ADVERB,
    PREPOSITION,
    CONJUNCTION,
    UNKNOWN,
    ALL;

    companion object {
        fun valueOf(partOfSpeechString: String?): PartOfSpeechDto {
            return values().firstOrNull { it.name.equals(partOfSpeechString, true) } ?: UNKNOWN
        }
    }
}
