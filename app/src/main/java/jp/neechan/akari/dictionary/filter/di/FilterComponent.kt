package jp.neechan.akari.dictionary.filter.di

import dagger.Component
import jp.neechan.akari.dictionary.base.di.AppComponent
import jp.neechan.akari.dictionary.base.di.scopes.PerFragment
import jp.neechan.akari.dictionary.filter.presentation.WordsFilterDialog

@PerFragment
@Component(dependencies = [AppComponent::class], modules = [FilterModule::class])
interface FilterComponent {

    fun inject(wordsFilterDialog: WordsFilterDialog)

    companion object {
        fun create(appComponent: AppComponent): FilterComponent {
            return DaggerFilterComponent.builder().appComponent(appComponent).build()
        }
    }
}