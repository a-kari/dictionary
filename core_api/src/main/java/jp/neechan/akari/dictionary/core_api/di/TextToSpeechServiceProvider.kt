package jp.neechan.akari.dictionary.core_api.di

import jp.neechan.akari.dictionary.core_api.domain.usecases.TextToSpeechPreferencesRepository
import jp.neechan.akari.dictionary.core_api.domain.usecases.TextToSpeechService

interface TextToSpeechServiceProvider {

    fun provideTextToSpeechService(): TextToSpeechService

    fun provideTextToSpeechPreferencesRepository(): TextToSpeechPreferencesRepository
}