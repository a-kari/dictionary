package jp.neechan.akari.dictionary.common.models.models

import jp.neechan.akari.dictionary.R

sealed class Result<out T> {

    data class Success<out T>(val value: T) : Result<T>()

    open class Error(val error: Throwable? = null,
                     val errorMessageResource: Int = R.string.network_unknown_error) : Result<Nothing>()

    object ConnectionError : Error(errorMessageResource = R.string.network_connection_error)

    object NotFoundError : Error(errorMessageResource = R.string.network_not_found_error)

    object Loading : Result<Nothing>()
}