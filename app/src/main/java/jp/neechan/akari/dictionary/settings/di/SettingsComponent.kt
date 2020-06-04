package jp.neechan.akari.dictionary.settings.di

import dagger.Component
import jp.neechan.akari.dictionary.base.di.AppComponent
import jp.neechan.akari.dictionary.base.di.scopes.PerFragment
import jp.neechan.akari.dictionary.settings.presentation.SettingsFragment

@PerFragment
@Component(dependencies = [AppComponent::class], modules = [SettingsModule::class])
interface SettingsComponent {

    fun inject(settingsFragment: SettingsFragment)
}