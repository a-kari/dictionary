package jp.neechan.akari.dictionary.discover

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import jp.neechan.akari.dictionary.discover.filter.WordsFilterParams
import kotlinx.coroutines.CoroutineScope

class WordsRemoteDataSourceFactory(private val wordsApiService: WordsApiService,
                                   private var wordsFilterParams: WordsFilterParams,
                                   private val coroutineScope: CoroutineScope) : DataSource.Factory<Int, String>() {

    private val _wordsDataSource = MutableLiveData<WordsRemoteDataSource>()
    val wordsRemoteDataSource: LiveData<WordsRemoteDataSource> = _wordsDataSource

    override fun create(): DataSource<Int, String> {
        val wordsDataSource = WordsRemoteDataSource(wordsApiService, wordsFilterParams, coroutineScope)
        _wordsDataSource.postValue(wordsDataSource)
        return wordsDataSource
    }

    fun setWordsFilterParams(wordsFilterParams: WordsFilterParams) {
        this.wordsFilterParams = wordsFilterParams
        invalidate()
    }

    private fun invalidate() {
        _wordsDataSource.value?.invalidate()
    }
}