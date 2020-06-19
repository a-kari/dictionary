package jp.neechan.akari.dictionary.base_ui_words_list.presentation.datasources

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import jp.neechan.akari.dictionary.core_api.domain.entities.FilterParams
import jp.neechan.akari.dictionary.core_api.domain.entities.Page
import jp.neechan.akari.dictionary.core_api.domain.entities.Result
import jp.neechan.akari.dictionary.core_api.domain.entities.mappers.ModelMapper
import jp.neechan.akari.dictionary.core_api.presentation.models.UIState
import jp.neechan.akari.dictionary.domain_words.domain.LoadFilterParamsUseCase
import jp.neechan.akari.dictionary.domain_words.domain.LoadWordsUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Suppress("UNUSED_ANONYMOUS_PARAMETER")
class WordsDataSource(private val loadWords: LoadWordsUseCase,
                      private val loadFilterParams: LoadFilterParamsUseCase,
                      private val resultMapper: ModelMapper<Result<Page<String>>, UIState<Page<String>>>,
                      private val coroutineScope: CoroutineScope) : PageKeyedDataSource<Int, String>() {

    private val _uiStateLiveData = MutableLiveData<UIState<Page<String>>>()
    val uiStateLiveData: LiveData<UIState<Page<String>>> = _uiStateLiveData

    companion object {
        private const val FIRST_PAGE = FilterParams.DEFAULT_PAGE
    }

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, String>) {
        _uiStateLiveData.postValue(UIState.ShowLoading)
        loadPage(FIRST_PAGE) { words, previousPage, nextPage ->
            callback.onResult(words, null, nextPage)
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, String>) {
        loadPage(params.key) { words, previousPage, nextPage ->
            callback.onResult(words, previousPage)
        }
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, String>) {
        loadPage(params.key) { words, previousPage, nextPage ->
            callback.onResult(words, nextPage)
        }
    }

    private fun loadPage(page: Int, callback: (words: List<String>, previousPage: Int?, nextPage: Int?) -> Unit) {
        coroutineScope.launch {
            val params: FilterParams = loadFilterParams()
            params.page = page
            val uiState = resultMapper.mapToExternalLayer(loadWords(params))

            if (uiState is UIState.ShowContent) {
                val wordsPage = uiState.content
                val previousPage = if (wordsPage.pageNumber > FIRST_PAGE) wordsPage.pageNumber - 1 else null
                val nextPage = if (wordsPage.hasNextPage) wordsPage.pageNumber + 1 else null
                callback(wordsPage.content, previousPage, nextPage)
            }
            _uiStateLiveData.postValue(uiState)
        }
    }
}