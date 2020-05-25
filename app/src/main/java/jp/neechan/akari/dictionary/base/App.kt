package jp.neechan.akari.dictionary.base

import android.app.Application
import jp.neechan.akari.dictionary.base.di.*
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@App)
            androidLogger(Level.DEBUG)
            modules(
                NetworkModule.get(),
                DatabaseModule.get(),
                ServiceModule.get(),
                RepositoryModule.get(),
                ViewModelModule.get(),
                UseCasesModule.get(),
                UIModule.get()
            )
        }
    }
}