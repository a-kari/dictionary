package jp.neechan.akari.dictionary.base.data.interface_adapters

import jp.neechan.akari.dictionary.base.data.framework.dto.WordDto
import jp.neechan.akari.dictionary.base.domain.entities.Page

interface WordsRemoteSource {

    suspend fun loadWords(params: Map<String, String>): Page<String>

    suspend fun loadWord(word: String): WordDto
}