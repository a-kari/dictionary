package jp.neechan.akari.dictionary.core_impl.data.interface_adapters

import jp.neechan.akari.dictionary.core_api.domain.entities.Result

internal interface ResultWrapper {

    suspend fun <T> wrapWithResult(block: suspend () -> T): Result<T>
}