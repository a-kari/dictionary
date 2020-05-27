package jp.neechan.akari.dictionary.base.di

import jp.neechan.akari.dictionary.base.data.framework.dto.mappers.DefinitionToDefinitionDtoMapper
import jp.neechan.akari.dictionary.base.data.framework.dto.mappers.FilterParamsToFilterParamsDtoMapper
import jp.neechan.akari.dictionary.base.data.framework.dto.mappers.FrequencyToFrequencyDtoMapper
import jp.neechan.akari.dictionary.base.data.framework.dto.mappers.PartOfSpeechToPartOfSpeechDtoMapper
import jp.neechan.akari.dictionary.base.data.framework.dto.mappers.WordToWordDtoMapper
import jp.neechan.akari.dictionary.base.data.interface_adapters.FilterParamsRepositoryImpl
import jp.neechan.akari.dictionary.base.data.interface_adapters.TextToSpeechPreferencesRepositoryImpl
import jp.neechan.akari.dictionary.base.data.interface_adapters.WordsRepositoryImpl
import jp.neechan.akari.dictionary.base.domain.entities.Page
import jp.neechan.akari.dictionary.base.domain.entities.Word
import jp.neechan.akari.dictionary.base.domain.usecases.FilterParamsRepository
import jp.neechan.akari.dictionary.base.domain.usecases.TextToSpeechPreferencesRepository
import jp.neechan.akari.dictionary.base.domain.usecases.WordsRepository
import jp.neechan.akari.dictionary.base.presentation.models.WordUI
import jp.neechan.akari.dictionary.base.presentation.models.mappers.ResultToUIStateMapper
import jp.neechan.akari.dictionary.base.presentation.models.mappers.WordToWordUIMapper
import org.koin.core.qualifier.StringQualifier
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

        single(qualifier = StringQualifier("ResultToUIStateMapper<Page<String>, Page<String>>")) {
            ResultToUIStateMapper<Page<String>, Page<String>>()
        }

        single(qualifier = StringQualifier("ResultToUIStateMapper<Word, WordUI>")) {
            ResultToUIStateMapper<Word, WordUI>(get(WordToWordUIMapper::class))
        }

        // Repositories
        single {
            WordsRepositoryImpl(get(), get(), get()) as WordsRepository
        }

        single {
            FilterParamsRepositoryImpl(get()) as FilterParamsRepository
        }

        single {
            TextToSpeechPreferencesRepositoryImpl(get()) as TextToSpeechPreferencesRepository
        }
    }
}