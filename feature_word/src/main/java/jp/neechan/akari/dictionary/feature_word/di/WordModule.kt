package jp.neechan.akari.dictionary.feature_word.di

import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import jp.neechan.akari.dictionary.feature_word.presentation.WordViewModelFactory

@Module
abstract class WordModule {

    @Binds
    abstract fun bindViewModelFactory(
        viewModelFactory: WordViewModelFactory
    ): ViewModelProvider.Factory
}