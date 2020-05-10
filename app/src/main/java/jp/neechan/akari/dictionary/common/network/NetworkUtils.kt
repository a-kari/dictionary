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

        } catch (unknownHostException: UnknownHostException) {
            Result.ConnectionError

        } catch (httpException: HttpException) {
            if (httpException.code() == HttpURLConnection.HTTP_NOT_FOUND) {
                Result.NotFoundError

            } else {
                Result.Error(httpException)
            }

        } catch (throwable: Throwable) {
            Result.Error(throwable)
        }
    }
}