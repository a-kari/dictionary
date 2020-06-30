package jp.neechan.akari.dictionary.core_impl.data.framework.db

import dagger.Reusable
import jp.neechan.akari.dictionary.core_api.domain.entities.Result
import jp.neechan.akari.dictionary.core_impl.data.interface_adapters.ResultWrapper
import javax.inject.Inject

@Reusable
class RoomResultWrapper @Inject constructor() : ResultWrapper {

    override suspend fun <T> wrapWithResult(block: suspend () -> T?): Result<T> {
        return try {
            val value = block()
            if (value == null || (value is Iterable<*> && value.none())) {
                Result.NotFoundError

            } else {
                Result.Success(value)
            }

        } catch (throwable: Throwable) {
            Result.Error(throwable)
        }
    }
}