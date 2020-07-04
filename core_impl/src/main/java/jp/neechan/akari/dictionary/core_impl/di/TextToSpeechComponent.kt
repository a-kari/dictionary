package jp.neechan.akari.dictionary.core_impl.di

import dagger.Component
import jp.neechan.akari.dictionary.core_api.di.ContextProvider
import jp.neechan.akari.dictionary.core_api.di.TextToSpeechServiceProvider
import jp.neechan.akari.dictionary.core_api.di.scopes.PerApp

@PerApp
@Component(
    modules = [TextToSpeechModule::class],
    dependencies = [ContextProvider::class, SharedPreferencesComponent::class]
)
interface TextToSpeechComponent : TextToSpeechServiceProvider {

    companion object {
        private var ttsComponent: TextToSpeechComponent? = null

        fun create(contextProvider: ContextProvider): TextToSpeechComponent {
            return ttsComponent
                ?: DaggerTextToSpeechComponent.builder()
                    .contextProvider(contextProvider)
                    .sharedPreferencesComponent(SharedPreferencesComponent.create(contextProvider))
                    .build()
                    .also { ttsComponent = it }
        }
    }
}
