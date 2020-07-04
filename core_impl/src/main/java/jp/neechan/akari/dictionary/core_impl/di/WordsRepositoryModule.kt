package jp.neechan.akari.dictionary.core_impl.di

import dagger.Binds
import dagger.Module
import jp.neechan.akari.dictionary.core_api.domain.usecases.FilterParamsRepository
import jp.neechan.akari.dictionary.core_api.domain.usecases.WordsRepository
import jp.neechan.akari.dictionary.core_impl.data.framework.db.dao.WordsLocalSourceImpl
import jp.neechan.akari.dictionary.core_impl.data.framework.network.WordsRemoteSourceImpl
import jp.neechan.akari.dictionary.core_impl.data.interface_adapters.FilterParamsRepositoryImpl
import jp.neechan.akari.dictionary.core_impl.data.interface_adapters.WordsLocalSource
import jp.neechan.akari.dictionary.core_impl.data.interface_adapters.WordsRemoteSource
import jp.neechan.akari.dictionary.core_impl.data.interface_adapters.WordsRepositoryImpl

@Module
internal abstract class WordsRepositoryModule {

    @Binds
    abstract fun bindWordsLocalSource(wordsLocalSourceImpl: WordsLocalSourceImpl): WordsLocalSource

    @Binds
    abstract fun bindWordsRemoteSource(wordsRemoteSourceImpl: WordsRemoteSourceImpl): WordsRemoteSource

    @Binds
    abstract fun bindWordsRepository(wordsRepositoryImpl: WordsRepositoryImpl): WordsRepository

    @Binds
    abstract fun bindFilterParamsRepository(filterParamsRepositoryImpl: FilterParamsRepositoryImpl): FilterParamsRepository
}
