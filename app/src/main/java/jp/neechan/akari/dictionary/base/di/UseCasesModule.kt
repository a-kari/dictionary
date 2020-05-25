package jp.neechan.akari.dictionary.base.di

import jp.neechan.akari.dictionary.home.domain.usecases.DeleteWordUseCase
import jp.neechan.akari.dictionary.home.domain.usecases.LoadSavedWordsUseCase
import jp.neechan.akari.dictionary.base.domain.usecases.LoadWordUseCase
import jp.neechan.akari.dictionary.word.domain.usecases.SaveWordUseCase
import jp.neechan.akari.dictionary.discover.domain.usecases.LoadAllWordsUseCase
import jp.neechan.akari.dictionary.base.domain.usecases.LoadFilterParamsUseCase
import jp.neechan.akari.dictionary.discover.filter.domain.usecases.SaveFilterParamsUseCase
import org.koin.core.module.Module
import org.koin.dsl.module

object UseCasesModule : KoinModule {

    override fun get(): Module {
        return module {
            single {
                LoadAllWordsUseCase(
                    get()
                )
            }
            single {
                LoadSavedWordsUseCase(
                    get()
                )
            }
            single { LoadWordUseCase(get()) }
            single {
                SaveWordUseCase(
                    get()
                )
            }
            single {
                DeleteWordUseCase(
                    get()
                )
            }
            single {
                LoadFilterParamsUseCase(
                    get()
                )
            }
            single {
                SaveFilterParamsUseCase(
                    get(),
                    get()
                )
            }
        }
    }
}