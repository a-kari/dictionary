package jp.neechan.akari.dictionary.common.models.models

import androidx.annotation.StringRes
import jp.neechan.akari.dictionary.R

sealed class Result<out T> {

    data class Success<out T>(val value: T) : Result<T>()

    open class Error(val error: Throwable? = null,
                     @StringRes val errorMessage: Int = R.string.network_unknown_error) : Result<Nothing>()

    object ConnectionError : Error(errorMessage = R.string.network_connection_error)

    object NotFoundError : Error(errorMessage = R.string.network_not_found_error)

    object Loading : Result<Nothing>()
}