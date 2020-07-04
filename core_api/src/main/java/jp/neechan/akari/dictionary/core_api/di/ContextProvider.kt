package jp.neechan.akari.dictionary.core_api.di

import android.content.Context

interface ContextProvider {

    fun provideContext(): Context
}
