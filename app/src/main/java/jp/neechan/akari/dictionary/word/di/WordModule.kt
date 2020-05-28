package jp.neechan.akari.dictionary.word.di

import jp.neechan.akari.dictionary.base.di.BaseModule
import jp.neechan.akari.dictionary.base.di.KoinModule
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.core.qualifier.named
import org.koin.dsl.module

object WordModule : KoinModule {

    override val module = module {
        provideUseCases()
        provideViewModels()
    }

    private fun Module.provideUseCases() {
        single { SaveWordUseCase(get()) }
    }

    private fun Module.provideViewModels() {
        viewModel {
            WordViewModel(
                get(),
                get(),
                get(ResultToUIStateMapper::class, named(BaseModule.resultWordToUIStateWordUIMapper)),
                get(WordToWordUIMapper::class),
                get(),
                get()
            )
        }
    }
}