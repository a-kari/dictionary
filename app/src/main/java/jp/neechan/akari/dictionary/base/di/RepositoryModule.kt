package jp.neechan.akari.dictionary.base.di

import jp.neechan.akari.dictionary.base.data.framework.dto.mappers.DefinitionToDefinitionDtoMapper
import jp.neechan.akari.dictionary.base.data.framework.dto.mappers.FilterParamsToFilterParamsDtoMapper
import jp.neechan.akari.dictionary.base.data.framework.dto.mappers.FrequencyToFrequencyDtoMapper
import jp.neechan.akari.dictionary.base.data.framework.dto.mappers.PartOfSpeechToPartOfSpeechDtoMapper
import jp.neechan.akari.dictionary.base.data.framework.dto.mappers.WordToWordDtoMapper
import jp.neechan.akari.dictionary.base.data.interface_adapters.FilterParamsRepositoryImpl
import jp.neechan.akari.dictionary.base.data.interface_adapters.WordsRepositoryImpl
import jp.neechan.akari.dictionary.base.domain.usecases.FilterParamsRepository
import jp.neechan.akari.dictionary.base.domain.usecases.WordsRepository
import org.koin.dsl.module

object RepositoryModule : KoinModule {

    override fun get() = module {
        // Mappers
        single {
            WordToWordDtoMapper(
                get(FrequencyToFrequencyDtoMapper::class),
                get(DefinitionToDefinitionDtoMapper::class)
            )
        }

        single { DefinitionToDefinitionDtoMapper(get(PartOfSpeechToPartOfSpeechDtoMapper::class)) }

        single { FrequencyToFrequencyDtoMapper() }

        single { PartOfSpeechToPartOfSpeechDtoMapper() }

        single {
            FilterParamsToFilterParamsDtoMapper(
                get(FrequencyToFrequencyDtoMapper::class),
                get(PartOfSpeechToPartOfSpeechDtoMapper::class)
            )
        }

        // Repositories
        single {
            WordsRepositoryImpl(
                get(),
                get(),
                get()
            ) as WordsRepository
        }

        single {
            FilterParamsRepositoryImpl(get()) as FilterParamsRepository
        }
    }
}