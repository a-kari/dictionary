package jp.neechan.akari.dictionary.feature_home.di

import dagger.Component
import jp.neechan.akari.dictionary.base_ui.di.scopes.PerFragment
import jp.neechan.akari.dictionary.core_api.di.ProvidersFacade
import jp.neechan.akari.dictionary.feature_home.presentation.HomeFragment

@PerFragment
@Component(modules = [HomeModule::class], dependencies = [ProvidersFacade::class])
internal interface HomeComponent {

    fun inject(homeFragment: HomeFragment)

    companion object {
        fun create(facade: ProvidersFacade): HomeComponent {
            return DaggerHomeComponent.builder().providersFacade(facade).build()
        }
    }
}