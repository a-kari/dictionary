package jp.neechan.akari.dictionary.word.di

import dagger.Component
import jp.neechan.akari.dictionary.base.di.AppComponent
import jp.neechan.akari.dictionary.base.di.scopes.PerFragment
import jp.neechan.akari.dictionary.word.presentation.WordFragment

@PerFragment
@Component(dependencies = [AppComponent::class], modules = [WordModule::class])
interface WordComponent {

    fun inject(wordFragment: WordFragment)
}