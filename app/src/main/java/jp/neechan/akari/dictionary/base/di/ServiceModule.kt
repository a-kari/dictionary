package jp.neechan.akari.dictionary.base.di

import jp.neechan.akari.dictionary.base.data.framework.tts.TextToSpeechService
import jp.neechan.akari.dictionary.base.data.framework.shared_preferences.FilterPreferencesServiceImpl
import jp.neechan.akari.dictionary.base.data.framework.tts.TextToSpeechPreferencesService
import jp.neechan.akari.dictionary.base.data.interface_adapters.FilterPreferencesService
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

object ServiceModule : KoinModule {

    override fun get() = module {
        single {
            TextToSpeechService(
                androidApplication(),
                get()
            )
        }

        single {
            FilterPreferencesServiceImpl(
                androidApplication()
            ) as FilterPreferencesService
        }

        single {
            TextToSpeechPreferencesService(
                androidApplication()
            )
        }
    }
}