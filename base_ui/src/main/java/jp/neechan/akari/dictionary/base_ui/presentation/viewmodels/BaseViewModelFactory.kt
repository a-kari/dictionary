package jp.neechan.akari.dictionary.base_ui.presentation.viewmodels

import androidx.lifecycle.ViewModelProvider

abstract class BaseViewModelFactory : ViewModelProvider.Factory {

    fun <T> cannotInstantiateException(modelClass: Class<T>): Throwable {
        return IllegalArgumentException("Cannot instantiate ViewModel: $modelClass")
    }
}
