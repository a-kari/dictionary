package jp.neechan.akari.dictionary.core_impl.di

import dagger.Component
import jp.neechan.akari.dictionary.core_api.di.ContextProvider
import jp.neechan.akari.dictionary.core_impl.data.interface_adapters.FilterPreferencesService
import jp.neechan.akari.dictionary.core_impl.data.interface_adapters.TextToSpeechPreferencesService
import javax.inject.Singleton

@Singleton
@Component(modules = [SharedPreferencesModule::class], dependencies = [ContextProvider::class])
interface SharedPreferencesComponent {

    fun provideFilterPreferencesService(): FilterPreferencesService

    fun provideTTSPreferencesService(): TextToSpeechPreferencesService

    companion object {
        private var preferencesComponent: SharedPreferencesComponent? = null

        fun create(contextProvider: ContextProvider): SharedPreferencesComponent {
            return preferencesComponent
                ?: DaggerSharedPreferencesComponent.builder()
                                                   .contextProvider(contextProvider)
                                                   .build()
                                                   .also { preferencesComponent = it }
        }
    }
}