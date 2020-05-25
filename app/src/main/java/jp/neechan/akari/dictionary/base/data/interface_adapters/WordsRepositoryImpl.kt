package jp.neechan.akari.dictionary.base.data.interface_adapters

import jp.neechan.akari.dictionary.base.data.framework.dto.FilterParamsDto
import jp.neechan.akari.dictionary.base.data.framework.dto.WordDto
import jp.neechan.akari.dictionary.base.domain.entities.FilterParams
import jp.neechan.akari.dictionary.base.domain.entities.Page
import jp.neechan.akari.dictionary.base.domain.entities.Result
import jp.neechan.akari.dictionary.base.domain.entities.Word
import jp.neechan.akari.dictionary.base.domain.entities.mappers.ModelMapper
import jp.neechan.akari.dictionary.base.domain.usecases.WordsRepository
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import java.util.Date

class WordsRepositoryImpl(private val localSource: WordsLocalSource,
                          private val remoteSource: WordsRemoteSource,
                          private val resultWrapper: ResultWrapper,
                          private val paramsMapper: ModelMapper<FilterParams, FilterParamsDto>,
                          private val wordMapper: ModelMapper<Word, WordDto>) : WordsRepository {

    override val allWordsRecentlyUpdated = ConflatedBroadcastChannel(false)

    override val savedWordsRecentlyUpdated = ConflatedBroadcastChannel(false)

    override suspend fun loadWords(params: FilterParams): Result<Page<String>> {
        val paramsDto = paramsMapper.mapToExternalLayer(params)
        return resultWrapper.wrapWithResult {
            remoteSource.loadWords(paramsDto.toMap())
        }
    }

    override suspend fun loadSavedWords(params: FilterParams): Result<Page<String>> {
        val page = params.page
        val limit = FilterParams.DEFAULT_PAGE_SIZE
        val offset = (page - 1) * limit
        val savedWords = localSource.getWords(limit, offset)

        return if (savedWords.isNotEmpty()) {
            val total = localSource.getWordsCount()
            val hasNextPage = total > page * limit
            val wordsPage = Page(savedWords, page, hasNextPage)
            Result.Success(wordsPage)

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
        val localWord = localSource.getWord(wordId)

        return if (localWord != null) {
            Result.Success(localWord.let {
                val word = it.word
                word.definitions = it.definitions
                wordMapper.mapToInternalLayer(word)
            })

        } else {
            resultWrapper.wrapWithResult({ remoteSource.loadWord(wordId) }, wordMapper)
        }
    }

    override suspend fun saveWord(word: Word) {
        val wordDto = wordMapper.mapToExternalLayer(word)
        wordDto.saveDate = Date()
        localSource.saveWord(wordDto)

        if (wordDto.definitions != null) {
            localSource.saveDefinitions(wordDto.definitions!!)
        }
    }

    override suspend fun deleteWord(wordId: String) {
        localSource.deleteWord(wordId)
    }
}