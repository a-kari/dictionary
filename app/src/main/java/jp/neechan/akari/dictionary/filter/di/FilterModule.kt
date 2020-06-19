package jp.neechan.akari.dictionary.filter.di

import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import jp.neechan.akari.dictionary.filter.presentation.FilterViewModelFactory

@Module
abstract class FilterModule {

    @Binds
    abstract fun bindViewModelFactory(
        viewModelFactory: FilterViewModelFactory
    ): ViewModelProvider.Factory
}