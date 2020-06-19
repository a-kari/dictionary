package jp.neechan.akari.dictionary.core_api.domain.entities

sealed class Result<out T> {

    data class Success<out T>(val value: T) : Result<T>()

    open class Error(val error: Throwable? = null) : Result<Nothing>()

    object ConnectionError : Error()

    object NotFoundError : Error()

    object Loading : Result<Nothing>()
}