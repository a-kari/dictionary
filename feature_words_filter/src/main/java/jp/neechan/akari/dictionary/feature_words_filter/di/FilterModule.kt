package jp.neechan.akari.dictionary.feature_words_filter.di

import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import jp.neechan.akari.dictionary.feature_words_filter.presentation.FilterViewModelFactory

@Module
internal abstract class FilterModule {

    @Binds
    abstract fun bindViewModelFactory(
        viewModelFactory: FilterViewModelFactory
    ): ViewModelProvider.Factory
}
