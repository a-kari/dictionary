package jp.neechan.akari.dictionary.base.di

import jp.neechan.akari.dictionary.base.domain.usecases.FilterParamsRepository
import jp.neechan.akari.dictionary.base.domain.usecases.TextToSpeechPreferencesRepository
import jp.neechan.akari.dictionary.base.domain.usecases.WordsRepository

interface RepositoryProvider {

    fun provideWordsRepository(): WordsRepository

    fun provideFilterParamsRepository(): FilterParamsRepository

    fun provideTextToSpeechPreferencesRepository(): TextToSpeechPreferencesRepository
}