package jp.neechan.akari.dictionary.feature_search_word.di

import dagger.Component
import jp.neechan.akari.dictionary.base_ui.di.scopes.PerActivity
import jp.neechan.akari.dictionary.core_api.di.ProvidersFacade
import jp.neechan.akari.dictionary.feature_search_word.presentation.SearchWordActivity

@PerActivity
@Component(modules = [SearchWordModule::class], dependencies = [ProvidersFacade::class])
internal interface SearchWordComponent {

    fun inject(searchWordActivity: SearchWordActivity)

    companion object {
        fun create(facade: ProvidersFacade): SearchWordComponent {
            return DaggerSearchWordComponent.builder().providersFacade(facade).build()
        }
    }
}
