package jp.neechan.akari.dictionary.discover

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import jp.neechan.akari.dictionary.common.models.models.Result
import jp.neechan.akari.dictionary.discover.filter.WordsFilterParams
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.net.HttpURLConnection
import java.net.UnknownHostException

@Suppress("UNUSED_ANONYMOUS_PARAMETER")
class WordsDataSource(private val wordsApiService: WordsApiService,
                      private val wordsFilterParams: WordsFilterParams,
                      private val coroutineScope: CoroutineScope) : PageKeyedDataSource<Int, String>() {

    private val _errorLiveData = MutableLiveData<Result.Error>()
    val errorLiveData: LiveData<Result.Error> = _errorLiveData

    companion object {
        private const val FIRST_PAGE = WordsFilterParams.DEFAULT_PAGE
    }

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, String>) {
        loadWords(FIRST_PAGE) { words, previousPage, nextPage ->
            callback.onResult(words, null, nextPage)
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, String>) {
        loadWords(params.key) { words, previousPage, nextPage ->
            callback.onResult(words, previousPage)
        }
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, String>) {
        loadWords(params.key) { words, previousPage, nextPage ->
            callback.onResult(words, nextPage)
        }
    }

    private fun loadWords(page: Int, callback: (words: List<String>, previousPage: Int?, nextPage: Int?) -> Unit) {
        coroutineScope.launch(coroutineExceptionHandler) {
            wordsFilterParams.page = page

            val wordsPage = wordsApiService.loadWords(wordsFilterParams.toMap())
            val words = wordsPage.content
            val previousPage = if (wordsPage.pageNumber > FIRST_PAGE) wordsPage.pageNumber - 1 else null
            val nextPage = if (wordsPage.hasNextPage) wordsPage.pageNumber + 1 else null
            callback(words, previousPage, nextPage)

            if (words.isEmpty()) {
                _errorLiveData.postValue(Result.NotFoundError)
            }
        }
    }

    // todo: Somehow merge this with NetworkUtils.
    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        _errorLiveData.postValue(
            if (throwable is UnknownHostException) {
                Result.ConnectionError

            } else if (throwable is HttpException && throwable.code() == HttpURLConnection.HTTP_NOT_FOUND) {
                Result.NotFoundError

            } else {
                Result.Error(throwable)
            }
        )
    }
}