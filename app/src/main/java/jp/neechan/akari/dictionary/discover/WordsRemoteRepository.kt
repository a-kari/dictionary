package jp.neechan.akari.dictionary.discover

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import jp.neechan.akari.dictionary.common.models.mappers.FrequencyMapper
import jp.neechan.akari.dictionary.common.models.mappers.PartOfSpeechMapper
import jp.neechan.akari.dictionary.common.models.mappers.WordMapper
import jp.neechan.akari.dictionary.common.models.models.Frequency
import jp.neechan.akari.dictionary.common.models.models.PartOfSpeech
import jp.neechan.akari.dictionary.common.models.models.Result
import jp.neechan.akari.dictionary.common.models.models.Word
import jp.neechan.akari.dictionary.common.network.makeApiCall
import jp.neechan.akari.dictionary.discover.filter.WordsFilterParams
import jp.neechan.akari.dictionary.discover.filter.WordsFilterPreferencesService
import kotlinx.coroutines.CoroutineScope

class WordsRemoteRepository(private val wordsApiService: WordsApiService,
                            private val wordsPreferencesService: WordsFilterPreferencesService,
                            private val wordMapper: WordMapper,
                            private val frequencyMapper: FrequencyMapper,
                            private val partOfSpeechMapper: PartOfSpeechMapper) {

    private lateinit var wordsRemoteDataSourceFactory: WordsRemoteDataSourceFactory
    private val wordsFilterParams = wordsPreferencesService.loadWordsFilterParams()

    val wordsFilterFrequency: Frequency
        get() = frequencyMapper.mapToHigherLayer(wordsFilterParams.frequency)

    val wordsFilterPartOfSpeech: PartOfSpeech
        get() = partOfSpeechMapper.mapToHigherLayer(wordsFilterParams.partOfSpeech)

    fun subscribeToWords(coroutineScope: CoroutineScope): LiveData<PagedList<String>> {
        wordsRemoteDataSourceFactory = WordsRemoteDataSourceFactory(wordsApiService, wordsFilterParams, coroutineScope)
        val config = PagedList.Config.Builder()
                              .setEnablePlaceholders(false)
                              .setPageSize(WordsFilterParams.DEFAULT_PAGE_SIZE)
                              .build()
        return LivePagedListBuilder(wordsRemoteDataSourceFactory, config).build()
    }

    fun subscribeToWordsResult(): LiveData<Result<List<String>>> {
        return Transformations.switchMap(wordsRemoteDataSourceFactory.wordsRemoteDataSource) { wordsDataSource ->
            wordsDataSource.resultLiveData
        }
    }

    fun updateWordsFilterParams(frequency: Frequency, partOfSpeech: PartOfSpeech) {
        val partOfSpeechDto = partOfSpeechMapper.mapToLowerLayer(partOfSpeech)
        val frequencyDto = frequencyMapper.mapToLowerLayer(frequency)

        // Update only if the params were actually changed.
        if (wordsFilterParams.partOfSpeech != partOfSpeechDto || wordsFilterParams.frequency != frequencyDto) {
            wordsFilterParams.partOfSpeech = partOfSpeechDto
            wordsFilterParams.frequency = frequencyDto

            wordsRemoteDataSourceFactory.setWordsFilterParams(wordsFilterParams)
            wordsPreferencesService.saveWordsFilterParams(wordsFilterParams)
        }
    }

    suspend fun loadWord(word: String): Result<Word> {
        return makeApiCall {
            val wordDto = wordsApiService.loadWord(word)
            wordMapper.mapToHigherLayer(wordDto)
        }
    }
}
