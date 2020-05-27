package jp.neechan.akari.dictionary.home.di

import jp.neechan.akari.dictionary.base.di.BaseModule
import jp.neechan.akari.dictionary.base.di.KoinModule
import jp.neechan.akari.dictionary.base.presentation.models.mappers.ResultToUIStateMapper
import jp.neechan.akari.dictionary.home.domain.usecases.DeleteWordUseCase
import jp.neechan.akari.dictionary.home.domain.usecases.LoadSavedWordsUseCase
import jp.neechan.akari.dictionary.home.presentation.HomeViewModel
import jp.neechan.akari.dictionary.home.search.presentation.SearchWordViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.core.qualifier.named
import org.koin.dsl.module

object HomeModule : KoinModule {

    override val module = module {
        provideUseCases()
        provideViewModels()
    }

    private fun Module.provideUseCases() {
        single { LoadSavedWordsUseCase(get()) }

        single { DeleteWordUseCase(get()) }
    }

    private fun Module.provideViewModels() {
        viewModel {
            HomeViewModel(
                get(),
                get(),
                get(),
                get(ResultToUIStateMapper::class, named(BaseModule.resultPageStringToUIStatePageStringMapper))
            )
        }

        viewModel {
            SearchWordViewModel(
                get(),
                get(ResultToUIStateMapper::class, named(BaseModule.resultWordToUIStateWordUIMapper))
            )
        }
    }
}