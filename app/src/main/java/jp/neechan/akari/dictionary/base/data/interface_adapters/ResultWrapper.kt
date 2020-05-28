package jp.neechan.akari.dictionary.base.data.interface_adapters

import jp.neechan.akari.dictionary.base.domain.entities.Result

interface ResultWrapper {

    suspend fun <T> wrapWithResult(block: suspend () -> T): Result<T>
}