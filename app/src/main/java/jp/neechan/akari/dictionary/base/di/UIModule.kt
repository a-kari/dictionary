package jp.neechan.akari.dictionary.base.di

import jp.neechan.akari.dictionary.base.presentation.models.mappers.DefinitionToDefinitionUIMapper
import jp.neechan.akari.dictionary.base.presentation.models.mappers.FrequencyToFrequencyUIMapper
import jp.neechan.akari.dictionary.base.presentation.models.mappers.PartOfSpeechToPartOfSpeechUIMapper
import jp.neechan.akari.dictionary.base.presentation.models.mappers.WordToWordUIMapper
import org.koin.core.module.Module
import org.koin.dsl.module

object UIModule : KoinModule {

    override fun get(): Module {
        return module {
            // Mappers
            single {
                WordToWordUIMapper(
                    get(FrequencyToFrequencyUIMapper::class),
                    get(PartOfSpeechToPartOfSpeechUIMapper::class),
                    get(DefinitionToDefinitionUIMapper::class)
                )
            }

            single {
                DefinitionToDefinitionUIMapper(
                    get(PartOfSpeechToPartOfSpeechUIMapper::class)
                )
            }

            single { FrequencyToFrequencyUIMapper() }

            single { PartOfSpeechToPartOfSpeechUIMapper() }
        }
    }
}