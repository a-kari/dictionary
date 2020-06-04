package jp.neechan.akari.dictionary.search.di

import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import jp.neechan.akari.dictionary.search.presentation.SearchWordViewModelFactory

@Module
abstract class SearchWordModule {

    @Binds
    abstract fun bindViewModelFactory(
        viewModelFactory: SearchWordViewModelFactory
    ): ViewModelProvider.Factory
}