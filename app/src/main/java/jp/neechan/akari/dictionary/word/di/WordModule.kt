package jp.neechan.akari.dictionary.word.di

import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import jp.neechan.akari.dictionary.word.presentation.WordViewModelFactory

@Module
abstract class WordModule {

    @Binds
    abstract fun bindViewModelFactory(
        viewModelFactory: WordViewModelFactory
    ): ViewModelProvider.Factory
}