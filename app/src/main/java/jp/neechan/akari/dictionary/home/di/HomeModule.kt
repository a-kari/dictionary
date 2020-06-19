package jp.neechan.akari.dictionary.home.di

import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import jp.neechan.akari.dictionary.home.presentation.HomeViewModelFactory

@Module
abstract class HomeModule {

    @Binds
    abstract fun bindViewModelFactory(
        viewModelFactory: HomeViewModelFactory
    ): ViewModelProvider.Factory
}