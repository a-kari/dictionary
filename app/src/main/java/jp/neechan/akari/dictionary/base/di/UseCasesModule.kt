package jp.neechan.akari.dictionary.base.di

import jp.neechan.akari.dictionary.base.domain.usecases.LoadFilterParamsUseCase
import jp.neechan.akari.dictionary.base.domain.usecases.LoadWordUseCase
import jp.neechan.akari.dictionary.base.domain.usecases.SpeakUseCase
import jp.neechan.akari.dictionary.base.domain.usecases.StopSpeakingUseCase
import jp.neechan.akari.dictionary.discover.domain.usecases.LoadAllWordsUseCase
import jp.neechan.akari.dictionary.discover.filter.domain.usecases.SaveFilterParamsUseCase
import jp.neechan.akari.dictionary.home.domain.usecases.DeleteWordUseCase
import jp.neechan.akari.dictionary.home.domain.usecases.LoadSavedWordsUseCase
import jp.neechan.akari.dictionary.settings.domain.usecases.LoadPreferredPronunciationUseCase
import jp.neechan.akari.dictionary.settings.domain.usecases.LoadPreferredVoiceUseCase
import jp.neechan.akari.dictionary.settings.domain.usecases.LoadPronunciationsUseCase
import jp.neechan.akari.dictionary.settings.domain.usecases.LoadVoicesUseCase
import jp.neechan.akari.dictionary.settings.domain.usecases.SavePreferredPronunciationUseCase
import jp.neechan.akari.dictionary.settings.domain.usecases.SavePreferredVoiceUseCase
import jp.neechan.akari.dictionary.word.domain.usecases.SaveWordUseCase
import org.koin.core.module.Module
import org.koin.dsl.module

object UseCasesModule : KoinModule {

    override fun get(): Module {
        return module {
            single { LoadAllWordsUseCase(get()) }
            single { LoadSavedWordsUseCase(get()) }
            single { LoadWordUseCase(get()) }
            single { SaveWordUseCase(get()) }
            single { DeleteWordUseCase(get()) }
            single { LoadFilterParamsUseCase(get()) }
            single { SaveFilterParamsUseCase(get(), get()) }

            single { LoadPronunciationsUseCase(get()) }
            single { LoadVoicesUseCase(get()) }
            single { LoadPreferredPronunciationUseCase(get(), get()) }
            single { LoadPreferredVoiceUseCase(get(), get()) }
            single { SavePreferredPronunciationUseCase(get(), get()) }
            single { SavePreferredVoiceUseCase(get(), get()) }
            single { SpeakUseCase(get()) }
            single { StopSpeakingUseCase(get()) }
        }
    }
}