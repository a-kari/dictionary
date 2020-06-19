package jp.neechan.akari.dictionary.base.di

import dagger.Binds
import dagger.Module
import jp.neechan.akari.dictionary.base.data.framework.tts.TextToSpeechServiceImpl
import jp.neechan.akari.dictionary.base.domain.usecases.TextToSpeechService

@Module
abstract class TextToSpeechModule {

    @Binds
    abstract fun bindTTSService(textToSpeechServiceImpl: TextToSpeechServiceImpl): TextToSpeechService
}