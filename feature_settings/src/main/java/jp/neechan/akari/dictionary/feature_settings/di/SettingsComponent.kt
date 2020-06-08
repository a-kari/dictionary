package jp.neechan.akari.dictionary.feature_settings.di

import dagger.Component
import jp.neechan.akari.dictionary.base_ui.di.scopes.PerFragment
import jp.neechan.akari.dictionary.core_api.di.ProvidersFacade
import jp.neechan.akari.dictionary.feature_settings.presentation.SettingsFragment

@PerFragment
@Component(modules = [SettingsModule::class], dependencies = [ProvidersFacade::class])
internal interface SettingsComponent {

    fun inject(settingsFragment: SettingsFragment)

    companion object {
        fun create(facade: ProvidersFacade): SettingsComponent {
            return DaggerSettingsComponent.builder().providersFacade(facade).build()
        }
    }
}