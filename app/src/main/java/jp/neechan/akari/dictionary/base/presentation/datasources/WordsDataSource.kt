package jp.neechan.akari.dictionary.base.presentation.datasources

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import jp.neechan.akari.dictionary.base.domain.entities.FilterParams
import jp.neechan.akari.dictionary.base.domain.entities.Page
import jp.neechan.akari.dictionary.base.domain.entities.Result
import jp.neechan.akari.dictionary.base.domain.usecases.LoadFilterParamsUseCase
import jp.neechan.akari.dictionary.base.domain.usecases.LoadWordsUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Suppress("UNUSED_ANONYMOUS_PARAMETER")
class WordsDataSource(private val loadWords: LoadWordsUseCase,
                      private val loadFilterParams: LoadFilterParamsUseCase,
                      private val coroutineScope: CoroutineScope) : PageKeyedDataSource<Int, String>() {

    private val _resultLiveData = MutableLiveData<Result<Page<String>>>()
    val resultLiveData: LiveData<Result<Page<String>>> = _resultLiveData

    companion object {
        private const val FIRST_PAGE = FilterParams.DEFAULT_PAGE
    }

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, String>) {
        _resultLiveData.postValue(Result.Loading)
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
            val result = loadWords(params)

            if (result is Result.Success) {
                val wordsPage = result.value
                val previousPage = if (wordsPage.pageNumber > FIRST_PAGE) wordsPage.pageNumber - 1 else null
                val nextPage = if (wordsPage.hasNextPage) wordsPage.pageNumber + 1 else null
                callback(wordsPage.content, previousPage, nextPage)
            }
            _resultLiveData.postValue(result)
        }
    }
}