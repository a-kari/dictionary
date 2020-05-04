package jp.neechan.akari.dictionary

import android.app.Application
import jp.neechan.akari.dictionary.common.di.KoinModule
import org.koin.core.context.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin { modules(KoinModule.module) }
    }
}