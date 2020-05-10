package jp.neechan.akari.dictionary.discover

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import kotlinx.coroutines.CoroutineScope

class WordsDataSourceFactory(private val wordsApiService: WordsApiService,
                             private var loadWordsParams: Map<String, String>,
                             private val coroutineScope: CoroutineScope) : DataSource.Factory<Int, String>() {

    private val _wordsDataSource = MutableLiveData<WordsDataSource>()
    val wordsDataSource: LiveData<WordsDataSource> = _wordsDataSource

    override fun create(): DataSource<Int, String> {
        val wordsDataSource = WordsDataSource(wordsApiService, loadWordsParams, coroutineScope)
        _wordsDataSource.postValue(wordsDataSource)
        return wordsDataSource
    }

    fun setLoadWordsParams(loadWordsParams: Map<String, String>) {
        this.loadWordsParams = loadWordsParams
        invalidate()
    }

    private fun invalidate() {
        _wordsDataSource.value?.invalidate()
    }
}