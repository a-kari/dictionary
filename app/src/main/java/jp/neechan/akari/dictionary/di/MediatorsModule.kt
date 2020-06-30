package jp.neechan.akari.dictionary.di

import dagger.Binds
import dagger.Module
import jp.neechan.akari.dictionary.core_api.presentation.mediators.DiscoverMediator
import jp.neechan.akari.dictionary.core_api.presentation.mediators.HomeMediator
import jp.neechan.akari.dictionary.core_api.presentation.mediators.SearchWordMediator
import jp.neechan.akari.dictionary.core_api.presentation.mediators.SettingsMediator
import jp.neechan.akari.dictionary.core_api.presentation.mediators.WordMediator
import jp.neechan.akari.dictionary.core_api.presentation.mediators.WordsFilterMediator
import jp.neechan.akari.dictionary.feature_discover.presentation.DiscoverMediatorImpl
import jp.neechan.akari.dictionary.feature_home.presentation.HomeMediatorImpl
import jp.neechan.akari.dictionary.feature_search_word.presentation.SearchWordMediatorImpl
import jp.neechan.akari.dictionary.feature_settings.presentation.SettingsMediatorImpl
import jp.neechan.akari.dictionary.feature_word.presentation.WordMediatorImpl
import jp.neechan.akari.dictionary.feature_words_filter.presentation.WordsFilterMediatorImpl

@Module
abstract class MediatorsModule {

    @Binds
    abstract fun bindHomeMediator(mediatorImpl: HomeMediatorImpl): HomeMediator

    @Binds
    abstract fun bindDiscoverMediator(mediatorImpl: DiscoverMediatorImpl): DiscoverMediator

    @Binds
    abstract fun bindSettingsMediator(mediatorImpl: SettingsMediatorImpl): SettingsMediator

    @Binds
    abstract fun bindWordMediator(mediatorImpl: WordMediatorImpl): WordMediator

    @Binds
    abstract fun bindSearchWordMediator(mediatorImpl: SearchWordMediatorImpl): SearchWordMediator

    @Binds
    abstract fun bindWordsFilterMediator(mediatorImpl: WordsFilterMediatorImpl): WordsFilterMediator
}