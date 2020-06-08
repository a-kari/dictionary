package jp.neechan.akari.dictionary.feature_discover.di

import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import jp.neechan.akari.dictionary.feature_discover.presentation.DiscoverViewModelFactory

@Module
abstract class DiscoverModule {

    @Binds
    abstract fun bindViewModelFactory(
        viewModelFactory: DiscoverViewModelFactory
    ): ViewModelProvider.Factory
}