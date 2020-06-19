package jp.neechan.akari.dictionary.feature_discover.presentation

import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import jp.neechan.akari.dictionary.base_ui_words_list.presentation.datasources.WordsDataSourceFactory
import jp.neechan.akari.dictionary.core_api.domain.entities.FilterParams
import jp.neechan.akari.dictionary.core_api.domain.entities.Page
import jp.neechan.akari.dictionary.core_api.domain.entities.Result
import jp.neechan.akari.dictionary.core_api.domain.entities.mappers.ModelMapper
import jp.neechan.akari.dictionary.core_api.presentation.models.UIState
import jp.neechan.akari.dictionary.domain_words.domain.LoadFilterParamsUseCase
import jp.neechan.akari.dictionary.feature_discover.domain.usecases.LoadAllWordsUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.launch

internal class DiscoverViewModel(
    private val loadWordsUseCase: LoadAllWordsUseCase,
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