package jp.neechan.akari.dictionary.feature_home.di

import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import jp.neechan.akari.dictionary.feature_home.presentation.HomeViewModelFactory

@Module
abstract class HomeModule {

    @Binds
    abstract fun bindViewModelFactory(
        viewModelFactory: HomeViewModelFactory
    ): ViewModelProvider.Factory
}