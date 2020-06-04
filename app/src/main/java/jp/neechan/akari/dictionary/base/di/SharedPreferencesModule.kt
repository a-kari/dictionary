package jp.neechan.akari.dictionary.base.di

import dagger.Binds
import dagger.Module
import jp.neechan.akari.dictionary.base.data.framework.shared_preferences.FilterPreferencesServiceImpl
import jp.neechan.akari.dictionary.base.data.framework.shared_preferences.TextToSpeechPreferencesServiceImpl
import jp.neechan.akari.dictionary.base.data.interface_adapters.FilterPreferencesService
import jp.neechan.akari.dictionary.base.data.interface_adapters.TextToSpeechPreferencesService

@Module
abstract class SharedPreferencesModule {

    @Binds
    abstract fun bindFilterPreferencesService(
        filterPreferencesServiceImpl: FilterPreferencesServiceImpl
    ): FilterPreferencesService

    @Binds
    abstract fun bindTTSPreferencesService(
        textToSpeechPreferencesServiceImpl: TextToSpeechPreferencesServiceImpl
    ): TextToSpeechPreferencesService
}