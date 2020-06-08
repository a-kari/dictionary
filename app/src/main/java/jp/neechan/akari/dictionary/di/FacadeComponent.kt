package jp.neechan.akari.dictionary.di

import android.app.Application
import dagger.Component
import jp.neechan.akari.dictionary.core.di.CoreProvidersFactory
import jp.neechan.akari.dictionary.core_api.di.ContextProvider
import jp.neechan.akari.dictionary.core_api.di.ProvidersFacade
import jp.neechan.akari.dictionary.core_api.di.TextToSpeechServiceProvider
import jp.neechan.akari.dictionary.core_api.di.UIMappersProvider
import jp.neechan.akari.dictionary.core_api.di.WordsRepositoryProvider
import jp.neechan.akari.dictionary.core_api.di.scopes.PerApp

@PerApp
@Component(
    modules = [MediatorsModule::class],
    dependencies = [
        ContextProvider::class,
        WordsRepositoryProvider::class,
        TextToSpeechServiceProvider::class,
        UIMappersProvider::class
    ]
)
interface FacadeComponent : ProvidersFacade {

    companion object {
        private var facadeComponent: FacadeComponent? = null

        fun create(context: Application): FacadeComponent {
            val contextProvider = ContextComponent.create(context)
            return facadeComponent ?:
                DaggerFacadeComponent.builder()
                    .contextProvider(contextProvider)
                    .wordsRepositoryProvider(CoreProvidersFactory.createWordsRepositoryProvider(contextProvider))
                    .textToSpeechServiceProvider(CoreProvidersFactory.createTextToSpeechProvider(contextProvider))
                    .uIMappersProvider(CoreProvidersFactory.createUIMappersProvider())
                    .build()
                    .also { facadeComponent = it }
        }
    }
}