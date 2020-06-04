package jp.neechan.akari.dictionary.search.di

import dagger.Component
import jp.neechan.akari.dictionary.base.di.AppComponent
import jp.neechan.akari.dictionary.base.di.scopes.PerActivity
import jp.neechan.akari.dictionary.search.presentation.SearchWordActivity

@PerActivity
@Component(dependencies = [AppComponent::class], modules = [SearchWordModule::class])
interface SearchWordComponent {

    fun inject(searchWordActivity: SearchWordActivity)
}