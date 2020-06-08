package jp.neechan.akari.dictionary.core_api.di

import jp.neechan.akari.dictionary.core_api.presentation.mediators.DiscoverMediator
import jp.neechan.akari.dictionary.core_api.presentation.mediators.HomeMediator
import jp.neechan.akari.dictionary.core_api.presentation.mediators.SearchWordMediator
import jp.neechan.akari.dictionary.core_api.presentation.mediators.SettingsMediator
import jp.neechan.akari.dictionary.core_api.presentation.mediators.WordMediator
import jp.neechan.akari.dictionary.core_api.presentation.mediators.WordsFilterMediator

interface MediatorsProvider {

    fun provideHomeMediator(): HomeMediator

    fun provideDiscoverMediator(): DiscoverMediator

    fun provideSettingsMediator(): SettingsMediator

    fun provideWordMediator(): WordMediator

    fun provideSearchWordMediator(): SearchWordMediator

    fun provideWordsFilterMediator(): WordsFilterMediator
}