package jp.neechan.akari.dictionary.feature_main.di

import dagger.Component
import jp.neechan.akari.dictionary.base_ui.di.scopes.PerActivity
import jp.neechan.akari.dictionary.core_api.di.ProvidersFacade
import jp.neechan.akari.dictionary.feature_main.presentation.MainActivity

@PerActivity
@Component(dependencies = [ProvidersFacade::class])
internal interface MainComponent {

    fun inject(mainActivity: MainActivity)

    companion object {
        fun create(facade: ProvidersFacade): MainComponent {
            return DaggerMainComponent.builder().providersFacade(facade).build()
        }
    }
}
