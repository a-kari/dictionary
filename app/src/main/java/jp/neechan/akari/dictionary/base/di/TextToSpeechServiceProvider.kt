package jp.neechan.akari.dictionary.base.di

import jp.neechan.akari.dictionary.base.domain.usecases.TextToSpeechService

interface TextToSpeechServiceProvider {

    fun provideTextToSpeechService(): TextToSpeechService
}