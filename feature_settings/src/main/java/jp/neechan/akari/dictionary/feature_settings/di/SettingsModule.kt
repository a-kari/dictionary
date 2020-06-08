package jp.neechan.akari.dictionary.feature_settings.di

import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import jp.neechan.akari.dictionary.feature_settings.presentation.SettingsViewModelFactory

@Module
internal abstract class SettingsModule {

    @Binds
    abstract fun bindViewModelFactory(
        viewModelFactory: SettingsViewModelFactory
    ): ViewModelProvider.Factory
}