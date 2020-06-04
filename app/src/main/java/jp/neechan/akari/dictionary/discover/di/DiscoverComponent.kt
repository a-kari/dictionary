package jp.neechan.akari.dictionary.discover.di

import dagger.Component
import jp.neechan.akari.dictionary.base.di.AppComponent
import jp.neechan.akari.dictionary.base.di.scopes.PerFragment
import jp.neechan.akari.dictionary.discover.presentation.DiscoverFragment

@PerFragment
@Component(dependencies = [AppComponent::class], modules = [DiscoverModule::class])
interface DiscoverComponent {

    fun inject(discoverFragment: DiscoverFragment)
}