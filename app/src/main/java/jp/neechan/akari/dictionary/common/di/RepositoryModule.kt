package jp.neechan.akari.dictionary.common.di

import jp.neechan.akari.dictionary.common.models.mappers.DefinitionMapper
import jp.neechan.akari.dictionary.common.models.mappers.FrequencyMapper
import jp.neechan.akari.dictionary.common.models.mappers.PartOfSpeechMapper
import jp.neechan.akari.dictionary.common.models.mappers.WordMapper
import jp.neechan.akari.dictionary.discover.WordsRemoteRepository
import org.koin.dsl.module

object RepositoryModule : KoinModule {

    override fun get() = module {
        // Mappers
        single { FrequencyMapper() }
        single { PartOfSpeechMapper() }
        single { DefinitionMapper(get()) }
        single { WordMapper(get(), get(), get()) }

        // Repositories
        single { WordsRemoteRepository(get(), get(), get(), get(), get()) }
    }
}