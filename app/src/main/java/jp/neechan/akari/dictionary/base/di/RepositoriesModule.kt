package jp.neechan.akari.dictionary.base.di

import dagger.Binds
import dagger.Module
import jp.neechan.akari.dictionary.base.data.interface_adapters.FilterParamsRepositoryImpl
import jp.neechan.akari.dictionary.base.data.interface_adapters.TextToSpeechPreferencesRepositoryImpl
import jp.neechan.akari.dictionary.base.data.interface_adapters.WordsRepositoryImpl
import jp.neechan.akari.dictionary.base.domain.usecases.FilterParamsRepository
import jp.neechan.akari.dictionary.base.domain.usecases.TextToSpeechPreferencesRepository
import jp.neechan.akari.dictionary.base.domain.usecases.WordsRepository

@Module
abstract class RepositoriesModule {

    @Binds
    abstract fun bindWordsRepository(
        wordsRepositoryImpl: WordsRepositoryImpl
    ): WordsRepository

    @Binds
    abstract fun bindFilterParamsRepository(
        filterParamsRepositoryImpl: FilterParamsRepositoryImpl
    ): FilterParamsRepository

    @Binds
    abstract fun bindTTSPreferencesRepository(
        ttsPreferencesRepositoryImpl: TextToSpeechPreferencesRepositoryImpl
    ): TextToSpeechPreferencesRepository
}