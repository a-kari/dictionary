package jp.neechan.akari.dictionary.settings.di

import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import jp.neechan.akari.dictionary.settings.presentation.SettingsViewModelFactory

@Module
abstract class SettingsModule {

    @Binds
    abstract fun bindViewModelFactory(
        viewModelFactory: SettingsViewModelFactory
    ): ViewModelProvider.Factory
}