package jp.neechan.akari.dictionary.base.di

import jp.neechan.akari.dictionary.base.presentation.models.mappers.FrequencyToFrequencyUIMapper
import jp.neechan.akari.dictionary.base.presentation.models.mappers.PartOfSpeechToPartOfSpeechUIMapper
import jp.neechan.akari.dictionary.base.presentation.models.mappers.WordToWordUIMapper
import jp.neechan.akari.dictionary.base.presentation.viewmodels.ViewModelFactory
import org.koin.dsl.module

object ViewModelModule : KoinModule {

    override fun get() = module {
        single {
            ViewModelFactory(
                get(),
                get(),
                get(),
                get(),
                get(),
                get(),
                get(),
                get(),
                get(WordToWordUIMapper::class),
                get(FrequencyToFrequencyUIMapper::class),
                get(PartOfSpeechToPartOfSpeechUIMapper::class)
            )
        }
    }
}