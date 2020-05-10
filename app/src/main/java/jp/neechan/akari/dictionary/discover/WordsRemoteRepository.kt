package jp.neechan.akari.dictionary.discover

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import jp.neechan.akari.dictionary.common.models.mappers.WordMapper
import jp.neechan.akari.dictionary.common.models.models.Result
import jp.neechan.akari.dictionary.common.models.models.Word
import jp.neechan.akari.dictionary.common.network.makeApiCall
import kotlinx.coroutines.CoroutineScope

class WordsRemoteRepository(private val wordsApiService: WordsApiService,
                            private val wordMapper: WordMapper) {

    private lateinit var wordsDataSourceFactory: WordsDataSourceFactory

    // todo: Do something with access level. Some of fields should be public, some private.
    // todo: Play with the params somehow, because I don't like how it looks like now.
    companion object {
        const val PARAMETER_PAGE = "page"
        const val PARAMETER_LETTER_PATTERN = "letterPattern"
        const val PARAMETER_HAS_DETAILS = "hasDetails"
        const val PARAMETER_PART_OF_SPEECH = "partOfSpeech"
        const val PARAMETER_FREQUENCY_MIN = "frequencyMin"
        const val PARAMETER_FREQUENCY_MAX = "frequencyMax"

        const val PARAMETER_PAGE_DEFAULT_VALUE = 1
        const val PARAMETER_PAGE_SIZE_DEFAULT_VALUE = 100
        const val PARAMETER_LETTER_PATTERN_DEFAULT_VALUE = "^[a-z]{3,}\$"
        const val PARAMETER_HAS_DETAILS_DEFAULT_VALUE = "definitions,examples"
        const val PARAMETER_PART_OF_SPEECH_DEFAULT_VALUE = "noun"
        const val PARAMETER_FREQUENCY_MIN_DEFAULT_VALUE = 5f
        const val PARAMETER_FREQUENCY_MAX_DEFAULT_VALUE = 10f

        val defaultLoadWordsParams = mapOf(
            PARAMETER_PAGE to PARAMETER_PAGE_DEFAULT_VALUE.toString(),
            PARAMETER_LETTER_PATTERN to PARAMETER_LETTER_PATTERN_DEFAULT_VALUE,
            PARAMETER_HAS_DETAILS to PARAMETER_HAS_DETAILS_DEFAULT_VALUE,
            PARAMETER_PART_OF_SPEECH to PARAMETER_PART_OF_SPEECH_DEFAULT_VALUE,
            PARAMETER_FREQUENCY_MIN to PARAMETER_FREQUENCY_MIN_DEFAULT_VALUE.toString(),
            PARAMETER_FREQUENCY_MAX to PARAMETER_FREQUENCY_MAX_DEFAULT_VALUE.toString()
        )
    }

    fun subscribeToWords(coroutineScope: CoroutineScope): LiveData<PagedList<String>> {
        wordsDataSourceFactory = WordsDataSourceFactory(wordsApiService, defaultLoadWordsParams, coroutineScope)
        val config = PagedList.Config.Builder()
                              .setEnablePlaceholders(false)
                              .setPageSize(PARAMETER_PAGE_SIZE_DEFAULT_VALUE)
                              .build()
        return LivePagedListBuilder(wordsDataSourceFactory, config).build()
    }

    fun subscribeToWordsError(): LiveData<Result.Error> {
        return Transformations.switchMap(wordsDataSourceFactory.wordsDataSource) { wordsDataSource ->
            wordsDataSource.errorLiveData
        }
    }

    // todo: Save filter params to user preferences.
    fun updateLoadWordsParams(loadWordsParams: Map<String, String>) {
        wordsDataSourceFactory.setLoadWordsParams(loadWordsParams)
    }

    suspend fun loadWord(word: String): Result<Word> {
        return makeApiCall {
            val wordDto = wordsApiService.loadWord(word)
            wordMapper.toModel(wordDto)
        }
    }
}
