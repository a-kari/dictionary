package jp.neechan.akari.dictionary.core_impl.data.framework.network

import dagger.Reusable
import jp.neechan.akari.dictionary.core_api.domain.entities.Result
import jp.neechan.akari.dictionary.core_impl.data.interface_adapters.ResultWrapper
import retrofit2.HttpException
import java.net.HttpURLConnection
import java.net.UnknownHostException
import javax.inject.Inject

@Reusable
class RetrofitResultWrapper @Inject constructor() : ResultWrapper {

    override suspend fun <T> wrapWithResult(block: suspend () -> T?): Result<T> {
        return try {
            val value = block()
            if (value == null || (value is Iterable<*> && value.none())) {
                Result.NotFoundError

            } else {
                Result.Success(value)
            }

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