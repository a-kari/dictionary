package jp.neechan.akari.dictionary.base.data.framework.network

import jp.neechan.akari.dictionary.base.data.interface_adapters.ResultWrapper
import jp.neechan.akari.dictionary.base.domain.entities.mappers.ModelMapper
import jp.neechan.akari.dictionary.base.domain.entities.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.net.HttpURLConnection
import java.net.UnknownHostException

class RetrofitResultWrapper : ResultWrapper {

    override suspend fun <T> wrapWithResult(block: suspend () -> T): Result<T> {
        return withContext(Dispatchers.IO) {
            try {
                Result.Success(block())

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

    override suspend fun <T, R> wrapWithResult(block: suspend () -> T, mapper: ModelMapper<R, T>): Result<R> {
        return withContext(Dispatchers.IO) {
            try {
                Result.Success(mapper.mapToInternalLayer(block()))

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
}