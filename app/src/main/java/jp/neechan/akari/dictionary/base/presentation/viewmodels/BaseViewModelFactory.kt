package jp.neechan.akari.dictionary.base.presentation.viewmodels

import androidx.lifecycle.ViewModelProvider

abstract class BaseViewModelFactory : ViewModelProvider.Factory {

    fun <T> cannotInstantiateException(modelClass: Class<T>): Throwable {
        return RuntimeException("Cannot instantiate ViewModel: $modelClass")
    }
}