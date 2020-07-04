package jp.neechan.akari.dictionary.core_impl.di

import dagger.Binds
import dagger.Module
import jp.neechan.akari.dictionary.core_api.domain.usecases.TextToSpeechPreferencesRepository
import jp.neechan.akari.dictionary.core_api.domain.usecases.TextToSpeechService
import jp.neechan.akari.dictionary.core_impl.data.framework.tts.TextToSpeechServiceImpl
import jp.neechan.akari.dictionary.core_impl.data.interface_adapters.TextToSpeechPreferencesRepositoryImpl

@Module
internal abstract class TextToSpeechModule {

    @Binds
    abstract fun bindTTSPreferencesRepository(
        ttsPreferencesRepositoryImpl: TextToSpeechPreferencesRepositoryImpl
    ): TextToSpeechPreferencesRepository

    @Binds
    abstract fun bindTTSService(
        textToSpeechServiceImpl: TextToSpeechServiceImpl
    ): TextToSpeechService
}
