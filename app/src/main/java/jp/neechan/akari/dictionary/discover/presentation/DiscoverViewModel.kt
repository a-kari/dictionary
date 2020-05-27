package jp.neechan.akari.dictionary.discover.presentation

import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import jp.neechan.akari.dictionary.base.domain.entities.FilterParams
import jp.neechan.akari.dictionary.base.domain.entities.Page
import jp.neechan.akari.dictionary.base.domain.entities.Result
import jp.neechan.akari.dictionary.base.domain.entities.mappers.ModelMapper
import jp.neechan.akari.dictionary.base.domain.usecases.LoadFilterParamsUseCase
import jp.neechan.akari.dictionary.base.presentation.datasources.WordsDataSourceFactory
import jp.neechan.akari.dictionary.base.presentation.models.UIState
import jp.neechan.akari.dictionary.discover.domain.usecases.LoadAllWordsUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.launch

class DiscoverViewModel(private val loadWordsUseCase: LoadAllWordsUseCase,
                        loadFilterParamsUseCase: LoadFilterParamsUseCase,
                        resultMapper: ModelMapper<Result<Page<String>>, UIState<Page<String>>>) : ViewModel() {

    private val ioScope = CoroutineScope(Dispatchers.IO + SupervisorJob())

    private val wordsDataSourceFactory = WordsDataSourceFactory(
        loadWordsUseCase,
        loadFilterParamsUseCase,
        resultMapper,
        ioScope
    )

    val wordsLiveData by lazy {
        val config = PagedList.Config.Builder()
                              .setEnablePlaceholders(false)
                              .setPageSize(FilterParams.DEFAULT_PAGE_SIZE)
                              .build()
        LivePagedListBuilder(wordsDataSourceFactory, config).build()
    }

    val uiStateLiveData = Transformations.switchMap(wordsDataSourceFactory.wordsDataSource) { wordsDataSource ->
        wordsDataSource.uiStateLiveData
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