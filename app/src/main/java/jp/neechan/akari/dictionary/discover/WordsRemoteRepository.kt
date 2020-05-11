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
import kotlinx.coroutines.CoroutineScope

class WordsRemoteRepository(private val wordsApiService: WordsApiService,
                            private val wordMapper: WordMapper,
                            private val frequencyMapper: FrequencyMapper,
                            private val partOfSpeechMapper: PartOfSpeechMapper) {

    private lateinit var wordsDataSourceFactory: WordsDataSourceFactory

    // todo: Get from user preferences.
    private val wordsFilterParams = WordsFilterParams()

    val wordsFilterFrequency: Frequency
        get() = frequencyMapper.mapToHigherLayer(wordsFilterParams.frequency)

    val wordsFilterPartOfSpeech: PartOfSpeech?
        get() = wordsFilterParams.partOfSpeech?.let { partOfSpeechMapper.mapToHigherLayer(it) }

    fun subscribeToWords(coroutineScope: CoroutineScope): LiveData<PagedList<String>> {
        wordsDataSourceFactory = WordsDataSourceFactory(wordsApiService, wordsFilterParams, coroutineScope)
        val config = PagedList.Config.Builder()
                              .setEnablePlaceholders(false)
                              .setPageSize(WordsFilterParams.DEFAULT_PAGE_SIZE)
                              .build()
        return LivePagedListBuilder(wordsDataSourceFactory, config).build()
    }

    fun subscribeToWordsError(): LiveData<Result.Error> {
        return Transformations.switchMap(wordsDataSourceFactory.wordsDataSource) { wordsDataSource ->
            wordsDataSource.errorLiveData
        }
    }

    // todo: Save filter params to user preferences.
    fun updateWordsFilterParams(frequency: Frequency, partOfSpeech: PartOfSpeech?) {
        wordsFilterParams.partOfSpeech = partOfSpeech?.let { partOfSpeechMapper.mapToLowerLayer(it) }
        wordsFilterParams.frequency = frequencyMapper.mapToLowerLayer(frequency)

        wordsDataSourceFactory.setWordsFilterParams(wordsFilterParams)
    }

    suspend fun loadWord(word: String): Result<Word> {
        return makeApiCall {
            val wordDto = wordsApiService.loadWord(word)
            wordMapper.mapToHigherLayer(wordDto)
        }
    }
}
