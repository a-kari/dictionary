package jp.neechan.akari.dictionary.discover

import jp.neechan.akari.dictionary.word.Word
import jp.neechan.akari.dictionary.word.WordMapper

class WordsRemoteRepository(private val wordsApiService: WordsApiService,
                            private val wordMapper: WordMapper) {

    companion object {
        private const val AUTHORIZATION_TOKEN_KEY = "X-Mashape-Key"
        private const val AUTHORIZATION_TOKEN_VALUE = "8395b7db36mshce928fb38d86eb8p1e163bjsn8070f6b090a6"

        private const val PARAMETER_PAGE = "page"
        private const val PARAMETER_LETTER_PATTERN = "letterPattern"
        private const val PARAMETER_HAS_DETAILS = "hasDetails"
        private const val PARAMETER_PART_OF_SPEECH = "partOfSpeech"
        private const val PARAMETER_FREQUENCY_MIN = "frequencyMin"
        private const val PARAMETER_FREQUENCY_MAX = "frequencyMax"

        private const val PARAMETER_PAGE_DEFAULT_VALUE = "1"
        private const val PARAMETER_LETTER_PATTERN_DEFAULT_VALUE = "^[a-z]{3,}\$"
        private const val PARAMETER_HAS_DETAILS_DEFAULT_VALUE = "definitions,examples"
        private const val PARAMETER_PART_OF_SPEECH_DEFAULT_VALUE = "noun"
        private const val PARAMETER_FREQUENCY_MIN_DEFAULT_VALUE = "5"
        private const val PARAMETER_FREQUENCY_MAX_DEFAULT_VALUE = "10"

        private val headers = mapOf(Pair(AUTHORIZATION_TOKEN_KEY, AUTHORIZATION_TOKEN_VALUE))
        private val defaultParameters = mapOf(
            Pair(PARAMETER_PAGE, PARAMETER_PAGE_DEFAULT_VALUE),
            Pair(PARAMETER_LETTER_PATTERN, PARAMETER_LETTER_PATTERN_DEFAULT_VALUE),
            Pair(PARAMETER_HAS_DETAILS, PARAMETER_HAS_DETAILS_DEFAULT_VALUE),
            Pair(PARAMETER_PART_OF_SPEECH, PARAMETER_PART_OF_SPEECH_DEFAULT_VALUE),
            Pair(PARAMETER_FREQUENCY_MIN, PARAMETER_FREQUENCY_MIN_DEFAULT_VALUE),
            Pair(PARAMETER_FREQUENCY_MAX, PARAMETER_FREQUENCY_MAX_DEFAULT_VALUE)
        )
    }

    // todo: Pagination
    suspend fun loadWords(): Page<String> {
        return wordsApiService.loadWords(headers, defaultParameters)
    }

    suspend fun loadWord(word: String): Word {
        val wordDto = wordsApiService.loadWord(headers, word)
        return wordMapper.dtoToWord(wordDto)
    }
}
