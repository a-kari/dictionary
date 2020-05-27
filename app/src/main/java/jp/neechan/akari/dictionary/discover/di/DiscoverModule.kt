package jp.neechan.akari.dictionary.discover.di

import jp.neechan.akari.dictionary.base.di.BaseModule
import jp.neechan.akari.dictionary.base.di.KoinModule
import jp.neechan.akari.dictionary.base.presentation.models.mappers.FrequencyToFrequencyUIMapper
import jp.neechan.akari.dictionary.base.presentation.models.mappers.PartOfSpeechToPartOfSpeechUIMapper
import jp.neechan.akari.dictionary.base.presentation.models.mappers.ResultToUIStateMapper
import jp.neechan.akari.dictionary.discover.domain.usecases.LoadAllWordsUseCase
import jp.neechan.akari.dictionary.discover.filter.domain.usecases.SaveFilterParamsUseCase
import jp.neechan.akari.dictionary.discover.filter.presentation.WordsFilterViewModel
import jp.neechan.akari.dictionary.discover.presentation.DiscoverViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.core.qualifier.named
import org.koin.dsl.module

object DiscoverModule : KoinModule {

    override val module = module {
        provideUseCases()
        provideViewModels()
    }

    private fun Module.provideUseCases() {
        single { LoadAllWordsUseCase(get()) }

        single { SaveFilterParamsUseCase(get(), get()) }
    }

    private fun Module.provideViewModels() {
        viewModel {
            DiscoverViewModel(
                get(),
                get(),
                get(ResultToUIStateMapper::class, named(BaseModule.resultPageStringToUIStatePageStringMapper))
            )
        }

        viewModel {
            WordsFilterViewModel(
                get(),
                get(),
                get(FrequencyToFrequencyUIMapper::class),
                get(PartOfSpeechToPartOfSpeechUIMapper::class)
            )
        }
    }
}