package jp.neechan.akari.dictionary.home.di

import dagger.Component
import jp.neechan.akari.dictionary.base.di.AppComponent
import jp.neechan.akari.dictionary.base.di.scopes.PerFragment
import jp.neechan.akari.dictionary.home.presentation.HomeFragment

@PerFragment
@Component(dependencies = [AppComponent::class], modules = [HomeModule::class])
interface HomeComponent {

    fun inject(homeFragment: HomeFragment)

    companion object {
        fun create(appComponent: AppComponent): HomeComponent {
            return DaggerHomeComponent.builder().appComponent(appComponent).build()
        }
    }
}