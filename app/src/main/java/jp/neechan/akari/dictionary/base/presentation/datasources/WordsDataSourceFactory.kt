package jp.neechan.akari.dictionary.base.presentation.datasources

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import jp.neechan.akari.dictionary.base.domain.usecases.LoadFilterParamsUseCase
import jp.neechan.akari.dictionary.base.domain.usecases.LoadWordsUseCase
import kotlinx.coroutines.CoroutineScope

class WordsDataSourceFactory(private val loadWords: LoadWordsUseCase,
                             private var loadFilterParams: LoadFilterParamsUseCase,
                             private val coroutineScope: CoroutineScope) : DataSource.Factory<Int, String>() {

    private val _wordsDataSource = MutableLiveData<WordsDataSource>()
    val wordsDataSource: LiveData<WordsDataSource> = _wordsDataSource

    override fun create(): DataSource<Int, String> {
        val wordsDataSource = WordsDataSource(loadWords, loadFilterParams, coroutineScope)
        _wordsDataSource.postValue(wordsDataSource)
        return wordsDataSource
    }

    fun invalidate() {
        _wordsDataSource.value?.invalidate()
    }
}