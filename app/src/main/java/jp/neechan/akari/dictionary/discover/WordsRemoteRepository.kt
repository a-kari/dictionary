package jp.neechan.akari.dictionary.discover

import jp.neechan.akari.dictionary.common.Page

class WordsRemoteRepository(private val wordsApiService: WordsApiService) {

    suspend fun loadWords(): Page<String> {
        val headers = hashMapOf(
            Pair("X-Mashape-Key", "8395b7db36mshce928fb38d86eb8p1e163bjsn8070f6b090a6")
        )

        val parameters = hashMapOf(
            Pair("page", "1"),
            Pair("letterPattern", "^[a-z]{3,}\$"),
            Pair("hasDetails", "definitions,examples"),
            Pair("partOfSpeech", "noun"),
            Pair("frequencyMin", "5"),
            Pair("frequencyMax", "10")
        )
        return wordsApiService.loadWords(headers, parameters)
    }
}
