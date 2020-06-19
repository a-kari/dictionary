package jp.neechan.akari.dictionary.base.data.framework.network

import dagger.Reusable
import jp.neechan.akari.dictionary.base.data.interface_adapters.ResultWrapper
import jp.neechan.akari.dictionary.base.domain.entities.Result
import retrofit2.HttpException
import java.net.HttpURLConnection
import java.net.UnknownHostException
import javax.inject.Inject

@Reusable
class RetrofitResultWrapper @Inject constructor() : ResultWrapper {

    override suspend fun <T> wrapWithResult(block: suspend () -> T): Result<T> {
        return try {
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