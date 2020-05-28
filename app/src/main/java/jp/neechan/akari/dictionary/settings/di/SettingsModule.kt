package jp.neechan.akari.dictionary.settings.di

import jp.neechan.akari.dictionary.base.di.KoinModule
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

object SettingsModule : KoinModule {

    override val module = module {
        provideUseCases()
        provideViewModels()
    }

    private fun Module.provideUseCases() {
        single { LoadPronunciationsUseCase(get()) }
        single { LoadVoicesUseCase(get()) }
        single { LoadPreferredPronunciationUseCase(get(), get()) }
        single { LoadPreferredVoiceUseCase(get(), get()) }
        single { SavePreferredPronunciationUseCase(get(), get()) }
        single { SavePreferredVoiceUseCase(get(), get()) }
    }

    private fun Module.provideViewModels() {
        viewModel { SettingsViewModel(get(), get(), get(), get(), get(), get(), get(), get()) }
    }
}