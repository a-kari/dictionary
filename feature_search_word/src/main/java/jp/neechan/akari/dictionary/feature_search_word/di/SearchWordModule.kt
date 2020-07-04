package jp.neechan.akari.dictionary.feature_search_word.di

import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import jp.neechan.akari.dictionary.feature_search_word.presentation.SearchWordViewModelFactory

@Module
internal abstract class SearchWordModule {

    @Binds
    abstract fun bindViewModelFactory(
        viewModelFactory: SearchWordViewModelFactory
    ): ViewModelProvider.Factory
}
