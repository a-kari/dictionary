package jp.neechan.akari.dictionary.discover.filter

import jp.neechan.akari.dictionary.common.models.dto.FrequencyDto
import jp.neechan.akari.dictionary.common.models.dto.PartOfSpeechDto

data class WordsFilterParams(var page: Int = DEFAULT_PAGE,
                             var partOfSpeech: PartOfSpeechDto = DEFAULT_PART_OF_SPEECH,
                             var frequency: FrequencyDto = DEFAULT_FREQUENCY) {

    companion object {
        const val DEFAULT_PAGE = 1
        const val DEFAULT_PAGE_SIZE = 100
        val DEFAULT_PART_OF_SPEECH = PartOfSpeechDto.NOUN
        val DEFAULT_FREQUENCY = FrequencyDto.FREQUENT

        private const val DEFAULT_LETTER_PATTERN = "^[A-Za-z]{1,}\$"
        private const val DEFAULT_HAS_DETAILS = "definitions,examples"
    }

    fun toMap(): Map<String, String> {
        // The backend does not receive "all" part of speech, but an empty string.
        val partOfSpeech = if (partOfSpeech != PartOfSpeechDto.ALL) {
            partOfSpeech.name.toLowerCase()

        } else {
            ""
        }

        return mapOf(
            "page" to page.toString(),
            "partOfSpeech" to partOfSpeech,
            "frequencyMin" to frequency.from.toString(),
            "frequencyMax" to frequency.to.toString(),
            "letterPattern" to DEFAULT_LETTER_PATTERN,
            "hasDetails" to DEFAULT_HAS_DETAILS
        )
    }
}