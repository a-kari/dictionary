package jp.neechan.akari.dictionary.discover.presentation

import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import jp.neechan.akari.dictionary.base.domain.entities.FilterParams
import jp.neechan.akari.dictionary.base.domain.usecases.LoadFilterParamsUseCase
import jp.neechan.akari.dictionary.base.presentation.datasources.WordsDataSourceFactory
import jp.neechan.akari.dictionary.discover.domain.usecases.LoadAllWordsUseCase
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.consumeEach

class DiscoverViewModel(private val loadWordsUseCase: LoadAllWordsUseCase,
                        loadFilterParamsUseCase: LoadFilterParamsUseCase) : ViewModel() {

    private val ioScope = CoroutineScope(Dispatchers.IO + SupervisorJob())

    private val wordsDataSourceFactory = WordsDataSourceFactory(loadWordsUseCase, loadFilterParamsUseCase, ioScope)

    val wordsLiveData by lazy {
        val config = PagedList.Config.Builder()
                              .setEnablePlaceholders(false)
                              .setPageSize(FilterParams.DEFAULT_PAGE_SIZE)
                              .build()
        LivePagedListBuilder(wordsDataSourceFactory, config).build()
    }

    val wordsStatusLiveData = Transformations.switchMap(wordsDataSourceFactory.wordsDataSource) { wordsDataSource ->
        wordsDataSource.resultLiveData
    }

    init {
        viewModelScope.launch {
            loadWordsUseCase.observeRecentlyUpdated().consumeEach { wordsRecentlyUpdated ->
                if (wordsRecentlyUpdated) {
                    wordsDataSourceFactory.invalidate()
                }
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        ioScope.cancel()
    }
}