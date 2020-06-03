package jp.neechan.akari.dictionary.base.data.interface_adapters

import jp.neechan.akari.dictionary.base.domain.entities.FilterParams
import jp.neechan.akari.dictionary.base.domain.entities.Page
import jp.neechan.akari.dictionary.base.domain.entities.Word

interface WordsRemoteSource {

    suspend fun loadWords(params: FilterParams): Page<String>

    suspend fun loadWord(wordId: String): Word
}