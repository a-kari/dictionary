package jp.neechan.akari.dictionary.core_impl.data.interface_adapters

import jp.neechan.akari.dictionary.core_api.domain.entities.FilterParams
import jp.neechan.akari.dictionary.core_api.domain.entities.Page
import jp.neechan.akari.dictionary.core_api.domain.entities.Result
import jp.neechan.akari.dictionary.core_api.domain.entities.Word
import jp.neechan.akari.dictionary.core_api.domain.usecases.WordsRepository
import jp.neechan.akari.dictionary.core_api.di.scopes.PerApp
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import javax.inject.Inject

@PerApp
internal class WordsRepositoryImpl @Inject constructor(
    private val localSource: WordsLocalSource,
    private val remoteSource: WordsRemoteSource,
    private val resultWrapper: ResultWrapper) : WordsRepository {

    override val allWordsRecentlyUpdated = ConflatedBroadcastChannel(false)

    override val savedWordsRecentlyUpdated = ConflatedBroadcastChannel(false)

    override suspend fun loadWords(params: FilterParams): Result<Page<String>> {
        return resultWrapper.wrapWithResult { remoteSource.loadWords(params) }
    }

    override suspend fun loadSavedWords(params: FilterParams): Result<Page<String>> {
        val savedWords = localSource.loadWords(params)
        return if (savedWords.isNotEmpty()) {
            Result.Success(savedWords)

        } else {
            Result.NotFoundError
        }
    }

    override suspend fun setAllWordsRecentlyUpdated(recentlyUpdated: Boolean) {
        allWordsRecentlyUpdated.send(recentlyUpdated)
    }

    override suspend fun setSavedWordsRecentlyUpdated(recentlyUpdated: Boolean) {
        savedWordsRecentlyUpdated.send(recentlyUpdated)
    }

    override suspend fun loadWord(wordId: String): Result<Word> {
        val localWord = localSource.loadWord(wordId)

        return if (localWord != null) {
            Result.Success(localWord)

        } else {
            resultWrapper.wrapWithResult { remoteSource.loadWord(wordId) }
        }
    }

    override suspend fun saveWord(word: Word) {
        localSource.saveWord(word)
    }

    override suspend fun deleteWord(wordId: String) {
        localSource.deleteWord(wordId)
    }
}