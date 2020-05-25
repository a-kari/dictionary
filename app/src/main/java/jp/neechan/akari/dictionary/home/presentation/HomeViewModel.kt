package jp.neechan.akari.dictionary.home.presentation

import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import jp.neechan.akari.dictionary.base.domain.entities.FilterParams
import jp.neechan.akari.dictionary.base.domain.usecases.LoadFilterParamsUseCase
import jp.neechan.akari.dictionary.base.presentation.datasources.WordsDataSourceFactory
import jp.neechan.akari.dictionary.home.domain.usecases.DeleteWordUseCase
import jp.neechan.akari.dictionary.home.domain.usecases.LoadSavedWordsUseCase
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.consumeEach

class HomeViewModel(private val loadWordsUseCase: LoadSavedWordsUseCase,
                    loadFilterParamsUseCase: LoadFilterParamsUseCase,
                    private val deleteWordUseCase: DeleteWordUseCase) : ViewModel() {

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

    var isEditMode = false

    init {
        viewModelScope.launch {
            loadWordsUseCase.observeRecentlyUpdated().consumeEach { wordsRecentlyUpdated ->
                if (wordsRecentlyUpdated) {
                    wordsDataSourceFactory.invalidate()
                }
            }
        }
    }

    fun deleteWord(word: String) = viewModelScope.launch(Dispatchers.IO) {
        deleteWordUseCase(word)
    }

    override fun onCleared() {
        super.onCleared()
        ioScope.cancel()
    }
}