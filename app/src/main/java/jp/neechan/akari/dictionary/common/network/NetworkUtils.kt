package jp.neechan.akari.dictionary.common.network

import jp.neechan.akari.dictionary.common.models.models.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.net.HttpURLConnection
import java.net.UnknownHostException

suspend inline fun <T> makeApiCall(crossinline apiCall: suspend () -> T): Result<T> {
    return withContext(Dispatchers.IO) {
        try {
            Result.Success(apiCall())

        // todo: Multiple catch blocks, just for refactoring.
        } catch (throwable: Throwable) {
            if (throwable is UnknownHostException) {
                Result.ConnectionError

            } else if (throwable is HttpException && throwable.code() == HttpURLConnection.HTTP_NOT_FOUND) {
                Result.NotFoundError

            } else {
                Result.Error(throwable)
            }
        }
    }
}