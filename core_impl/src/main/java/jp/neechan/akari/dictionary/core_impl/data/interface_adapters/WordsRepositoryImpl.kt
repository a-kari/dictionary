package jp.neechan.akari.dictionary.core_impl.data.interface_adapters

import jp.neechan.akari.dictionary.core_api.di.scopes.PerApp
import jp.neechan.akari.dictionary.core_api.domain.entities.FilterParams
import jp.neechan.akari.dictionary.core_api.domain.entities.Page
import jp.neechan.akari.dictionary.core_api.domain.entities.Result
import jp.neechan.akari.dictionary.core_api.domain.entities.Word
import jp.neechan.akari.dictionary.core_api.domain.usecases.WordsRepository
import jp.neechan.akari.dictionary.core_impl.di.qualifiers.LocalResultWrapper
import jp.neechan.akari.dictionary.core_impl.di.qualifiers.RemoteResultWrapper
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import javax.inject.Inject

@PerApp
internal class WordsRepositoryImpl @Inject constructor(
    private val localSource: WordsLocalSource,
    @LocalResultWrapper private val localResultWrapper: ResultWrapper,
    private val remoteSource: WordsRemoteSource,
    @RemoteResultWrapper private val remoteResultWrapper: ResultWrapper
) : WordsRepository {

    override val allWordsRecentlyUpdated = ConflatedBroadcastChannel(false)

    override val savedWordsRecentlyUpdated = ConflatedBroadcastChannel(false)

    override suspend fun loadWords(params: FilterParams): Result<Page<String>> {
        return remoteResultWrapper.wrapWithResult { remoteSource.loadWords(params) }
    }

    override suspend fun loadSavedWords(params: FilterParams): Result<Page<String>> {
        return localResultWrapper.wrapWithResult { localSource.loadWords(params) }
    }

    override suspend fun setAllWordsRecentlyUpdated(recentlyUpdated: Boolean) {
        allWordsRecentlyUpdated.send(recentlyUpdated)
    }

    override suspend fun setSavedWordsRecentlyUpdated(recentlyUpdated: Boolean) {
        savedWordsRecentlyUpdated.send(recentlyUpdated)
    }

    override suspend fun loadWord(wordId: String): Result<Word> {
        val localWord = localResultWrapper.wrapWithResult { localSource.loadWord(wordId) }

        return if (localWord is Result.Success<Word>) {
            localWord
        } else {
            remoteResultWrapper.wrapWithResult { remoteSource.loadWord(wordId) }
        }
    }

    override suspend fun saveWord(word: Word) {
        localSource.saveWord(word)
    }

    override suspend fun deleteWord(wordId: String) {
        localSource.deleteWord(wordId)
    }
}
