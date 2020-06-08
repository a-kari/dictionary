package jp.neechan.akari.dictionary.core_impl.di

import dagger.Binds
import dagger.Module
import jp.neechan.akari.dictionary.core_impl.data.framework.shared_preferences.FilterPreferencesServiceImpl
import jp.neechan.akari.dictionary.core_impl.data.framework.shared_preferences.TextToSpeechPreferencesServiceImpl
import jp.neechan.akari.dictionary.core_impl.data.interface_adapters.FilterPreferencesService
import jp.neechan.akari.dictionary.core_impl.data.interface_adapters.TextToSpeechPreferencesService

@Module
internal abstract class SharedPreferencesModule {

    @Binds
    abstract fun bindFilterPreferencesService(
        filterPreferencesServiceImpl: FilterPreferencesServiceImpl
    ): FilterPreferencesService

    @Binds
    abstract fun bindTTSPreferencesService(
        textToSpeechPreferencesServiceImpl: TextToSpeechPreferencesServiceImpl
    ): TextToSpeechPreferencesService
}