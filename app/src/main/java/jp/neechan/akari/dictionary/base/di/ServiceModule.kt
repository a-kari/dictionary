package jp.neechan.akari.dictionary.base.di

import jp.neechan.akari.dictionary.base.data.framework.shared_preferences.FilterPreferencesServiceImpl
import jp.neechan.akari.dictionary.base.data.framework.shared_preferences.TextToSpeechPreferencesServiceImpl
import jp.neechan.akari.dictionary.base.data.framework.tts.TextToSpeechServiceImpl
import jp.neechan.akari.dictionary.base.data.interface_adapters.FilterPreferencesService
import jp.neechan.akari.dictionary.base.data.interface_adapters.TextToSpeechPreferencesService
import jp.neechan.akari.dictionary.base.domain.usecases.TextToSpeechService
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

object ServiceModule : KoinModule {

    override fun get() = module {
        single {
            TextToSpeechServiceImpl(androidApplication(), get()) as TextToSpeechService
        }

        single {
            FilterPreferencesServiceImpl(androidApplication()) as FilterPreferencesService
        }

        single {
            TextToSpeechPreferencesServiceImpl(androidApplication()) as TextToSpeechPreferencesService
        }
    }
}