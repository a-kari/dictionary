package jp.neechan.akari.dictionary.common.di

import jp.neechan.akari.dictionary.common.viewmodels.ViewModelFactory
import org.koin.dsl.module

object ViewModelModule : KoinModule {

    override fun get() = module {
        single { ViewModelFactory(get(), get()) }
    }
}