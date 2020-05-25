package jp.neechan.akari.dictionary.base.di

import jp.neechan.akari.dictionary.base.data.interface_adapters.WordsRepositoryImpl
import jp.neechan.akari.dictionary.base.data.framework.dto.mappers.*
import jp.neechan.akari.dictionary.base.domain.usecases.WordsRepository
import jp.neechan.akari.dictionary.base.data.interface_adapters.FilterParamsRepositoryImpl
import jp.neechan.akari.dictionary.base.domain.usecases.FilterParamsRepository
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
                get(),
                get(FilterParamsToFilterParamsDtoMapper::class),
                get(WordToWordDtoMapper::class)
            ) as WordsRepository
        }

        single {
            FilterParamsRepositoryImpl(
                get(),
                get(FilterParamsToFilterParamsDtoMapper::class)
            ) as FilterParamsRepository
        }
    }
}