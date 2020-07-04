package jp.neechan.akari.dictionary.core_api.presentation.models

import androidx.annotation.StringRes
import jp.neechan.akari.dictionary.core_api.R

sealed class UIState<out T> {

    data class ShowContent<out T>(val content: T) : UIState<T>()

    open class ShowError(
        val error: Throwable? = null,
        @StringRes val errorMessage: Int = R.string.ui_state_unknown_error
    ) : UIState<Nothing>()

    object ShowConnectionError : ShowError(errorMessage = R.string.ui_state_connection_error)

    object ShowNotFoundError : ShowError(errorMessage = R.string.ui_state_not_found_error)

    object ShowLoading : UIState<Nothing>()
}
