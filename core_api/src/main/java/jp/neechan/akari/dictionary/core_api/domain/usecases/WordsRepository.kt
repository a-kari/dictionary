package jp.neechan.akari.dictionary.core_api.domain.usecases

import jp.neechan.akari.dictionary.core_api.domain.entities.FilterParams
import jp.neechan.akari.dictionary.core_api.domain.entities.Page
import jp.neechan.akari.dictionary.core_api.domain.entities.Result
import jp.neechan.akari.dictionary.core_api.domain.entities.Word
import kotlinx.coroutines.channels.ConflatedBroadcastChannel

interface WordsRepository {

    val allWordsRecentlyUpdated: ConflatedBroadcastChannel<Boolean>

    val savedWordsRecentlyUpdated: ConflatedBroadcastChannel<Boolean>

    suspend fun loadWords(params: FilterParams): Result<Page<String>>

    suspend fun loadSavedWords(params: FilterParams): Result<Page<String>>

    suspend fun setAllWordsRecentlyUpdated(recentlyUpdated: Boolean)

    suspend fun setSavedWordsRecentlyUpdated(recentlyUpdated: Boolean)

    suspend fun loadWord(wordId: String): Result<Word>

    suspend fun saveWord(word: Word)

    suspend fun deleteWord(wordId: String)
}