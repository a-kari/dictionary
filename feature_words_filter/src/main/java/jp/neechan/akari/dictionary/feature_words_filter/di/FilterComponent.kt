package jp.neechan.akari.dictionary.feature_words_filter.di

import dagger.Component
import jp.neechan.akari.dictionary.base_ui.di.scopes.PerFragment
import jp.neechan.akari.dictionary.core_api.di.ProvidersFacade
import jp.neechan.akari.dictionary.feature_words_filter.presentation.WordsFilterDialog

@PerFragment
@Component(modules = [FilterModule::class], dependencies = [ProvidersFacade::class])
interface FilterComponent {

    fun inject(wordsFilterDialog: WordsFilterDialog)

    companion object {
        fun create(facade: ProvidersFacade): FilterComponent {
            return DaggerFilterComponent.builder().providersFacade(facade).build()
        }
    }
}