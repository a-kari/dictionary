package jp.neechan.akari.dictionary.feature_home.presentation

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
import jp.neechan.akari.dictionary.feature_home.domain.usecases.DeleteWordUseCase
import jp.neechan.akari.dictionary.feature_home.domain.usecases.LoadSavedWordsUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.launch

class HomeViewModel(
    private val loadWordsUseCase: LoadSavedWordsUseCase,
    loadFilterParamsUseCase: LoadFilterParamsUseCase,
    private val deleteWordUseCase: DeleteWordUseCase,
    resultMapper: ModelMapper<Result<Page<String>>, UIState<Page<String>>>) : ViewModel() {

    // It's needed to use Paging Library's own background threads here for smooth work.
    // As deleting a word invalidates the whole DataSource, data will be re-fetched.
    //
    // And if re-fetching is not in Paging Library thread, it will cause
    // an excess empty list submitting before a fresh non-empty one,
    // which in turn leads to the whole RecyclerView state wiping and jumping to its beginning.
    private val unconfinedScope = CoroutineScope(Dispatchers.Unconfined + SupervisorJob())

    private val wordsDataSourceFactory = WordsDataSourceFactory(
        loadWordsUseCase,
        loadFilterParamsUseCase,
        resultMapper,
        unconfinedScope
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
        unconfinedScope.cancel()
    }
}