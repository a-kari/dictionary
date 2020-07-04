package jp.neechan.akari.dictionary.feature_discover.di

import dagger.Component
import jp.neechan.akari.dictionary.base_ui.di.scopes.PerFragment
import jp.neechan.akari.dictionary.core_api.di.ProvidersFacade
import jp.neechan.akari.dictionary.feature_discover.presentation.DiscoverFragment

@PerFragment
@Component(modules = [DiscoverModule::class], dependencies = [ProvidersFacade::class])
internal interface DiscoverComponent {

    fun inject(discoverFragment: DiscoverFragment)

    companion object {
        fun create(facade: ProvidersFacade): DiscoverComponent {
            return DaggerDiscoverComponent.builder().providersFacade(facade).build()
        }
    }
}
