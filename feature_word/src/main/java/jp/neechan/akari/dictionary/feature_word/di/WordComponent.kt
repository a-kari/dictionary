package jp.neechan.akari.dictionary.feature_word.di

import dagger.Component
import jp.neechan.akari.dictionary.base_ui.di.scopes.PerFragment
import jp.neechan.akari.dictionary.core_api.di.ProvidersFacade
import jp.neechan.akari.dictionary.feature_word.presentation.WordFragment

@PerFragment
@Component(modules = [WordModule::class], dependencies = [ProvidersFacade::class])
internal interface WordComponent {

    fun inject(wordFragment: WordFragment)

    companion object {
        fun create(facade: ProvidersFacade): WordComponent {
            return DaggerWordComponent.builder().providersFacade(facade).build()
        }
    }
}
