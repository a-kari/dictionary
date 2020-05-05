package jp.neechan.akari.dictionary.common.network

import jp.neechan.akari.dictionary.common.models.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.net.HttpURLConnection
import java.net.UnknownHostException

suspend inline fun <T> makeApiCall(crossinline apiCall: suspend () -> T): Result<T> {
    return withContext(Dispatchers.IO) {
        try {
            Result.Success(apiCall())

        } catch (throwable: Throwable) {
            if (throwable is UnknownHostException) {
                Result.ConnectionError(throwable)

            } else if (throwable is HttpException && throwable.code() == HttpURLConnection.HTTP_NOT_FOUND) {
                Result.NotFoundError(throwable)

            } else {
                Result.Error(throwable)
            }
        }
    }
}