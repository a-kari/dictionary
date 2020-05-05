package jp.neechan.akari.dictionary.common.di

import jp.neechan.akari.dictionary.common.services.TextToSpeechService
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

object ServiceModule : KoinModule {

    override fun get() = module {
        single { TextToSpeechService(androidApplication()) }
    }
}