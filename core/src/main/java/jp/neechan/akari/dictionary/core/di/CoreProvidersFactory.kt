package jp.neechan.akari.dictionary.core.di

import jp.neechan.akari.dictionary.core_api.di.ContextProvider
import jp.neechan.akari.dictionary.core_api.di.TextToSpeechServiceProvider
import jp.neechan.akari.dictionary.core_api.di.UIMappersProvider
import jp.neechan.akari.dictionary.core_api.di.WordsRepositoryProvider
import jp.neechan.akari.dictionary.core_impl.di.TextToSpeechComponent
import jp.neechan.akari.dictionary.core_impl.di.UIMappersComponent
import jp.neechan.akari.dictionary.core_impl.di.WordsRepositoryComponent

object CoreProvidersFactory {

    fun createWordsRepositoryProvider(contextProvider: ContextProvider): WordsRepositoryProvider {
        return WordsRepositoryComponent.create(contextProvider)
    }

    fun createTextToSpeechProvider(contextProvider: ContextProvider): TextToSpeechServiceProvider {
        return TextToSpeechComponent.create(contextProvider)
    }

    fun createUIMappersProvider(): UIMappersProvider {
        return UIMappersComponent.create()
    }
}
