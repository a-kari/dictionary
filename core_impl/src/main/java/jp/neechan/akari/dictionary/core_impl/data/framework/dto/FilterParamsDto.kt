package jp.neechan.akari.dictionary.core_impl.data.framework.dto

import jp.neechan.akari.dictionary.core_api.domain.entities.FilterParams

data class FilterParamsDto(var page: Int,
                                    var partOfSpeech: PartOfSpeechDto,
                                    var frequency: FrequencyDto) {

    companion object {
        const val DEFAULT_PAGE = FilterParams.DEFAULT_PAGE
        val DEFAULT_PART_OF_SPEECH = PartOfSpeechDto.valueOf(FilterParams.DEFAULT_PART_OF_SPEECH.name)
        val DEFAULT_FREQUENCY = FrequencyDto.valueOf(FilterParams.DEFAULT_FREQUENCY.name)

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