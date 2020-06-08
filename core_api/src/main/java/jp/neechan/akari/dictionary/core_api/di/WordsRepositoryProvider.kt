package jp.neechan.akari.dictionary.core_api.di

import jp.neechan.akari.dictionary.core_api.domain.usecases.FilterParamsRepository
import jp.neechan.akari.dictionary.core_api.domain.usecases.WordsRepository

interface WordsRepositoryProvider {

    fun provideWordsRepository(): WordsRepository

    fun provideWordsFilterParamsRepository(): FilterParamsRepository
}