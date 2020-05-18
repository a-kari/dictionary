package jp.neechan.akari.dictionary

import android.app.Application
import jp.neechan.akari.dictionary.common.di.*
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@App)
            modules(
                NetworkModule.get(),
                DatabaseModule.get(),
                ServiceModule.get(),
                RepositoryModule.get(),
                ViewModelModule.get()
            )
        }
    }
}