package jp.neechan.akari.dictionary.core_impl.data.interface_adapters

import jp.neechan.akari.dictionary.core_api.domain.entities.FilterParams
import jp.neechan.akari.dictionary.core_api.domain.entities.Page
import jp.neechan.akari.dictionary.core_api.domain.entities.Word

internal interface WordsRemoteSource {

    suspend fun loadWords(params: FilterParams): Page<String>

    suspend fun loadWord(wordId: String): Word
}
