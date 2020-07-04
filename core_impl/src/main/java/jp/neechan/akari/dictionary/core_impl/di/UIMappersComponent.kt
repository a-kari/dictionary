package jp.neechan.akari.dictionary.core_impl.di

import dagger.Component
import jp.neechan.akari.dictionary.core_api.di.UIMappersProvider
import jp.neechan.akari.dictionary.core_api.di.scopes.PerApp

@PerApp
@Component(modules = [UIMappersModule::class])
interface UIMappersComponent : UIMappersProvider {

    companion object {
        private var uiMappersComponent: UIMappersComponent? = null

        fun create(): UIMappersComponent {
            return uiMappersComponent ?: DaggerUIMappersComponent.create().also { uiMappersComponent = it }
        }
    }
}
