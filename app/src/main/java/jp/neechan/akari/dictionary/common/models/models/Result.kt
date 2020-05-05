package jp.neechan.akari.dictionary.common.models.models

import jp.neechan.akari.dictionary.R

sealed class Result<out T> {

    data class Success<out T>(val value: T) : Result<T>()

    open class Error(
        val error: Throwable,
        val errorMessageResource: Int = R.string.network_unknown_error
    ) : Result<Nothing>()

    class ConnectionError(error: Throwable) : Error(error, R.string.network_connection_error)

    class NotFoundError(error: Throwable) : Error(error, R.string.network_not_found_error)
}