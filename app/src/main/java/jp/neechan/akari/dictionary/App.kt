package jp.neechan.akari.dictionary

import android.app.Application
import jp.neechan.akari.dictionary.base.di.BaseModule
import jp.neechan.akari.dictionary.base.di.BaseProperties
import jp.neechan.akari.dictionary.discover.di.DiscoverModule
import jp.neechan.akari.dictionary.home.di.HomeModule
import jp.neechan.akari.dictionary.settings.di.SettingsModule
import jp.neechan.akari.dictionary.word.di.WordModule
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
                BaseModule.module,
                HomeModule.module,
                DiscoverModule.module,
                SettingsModule.module,
                WordModule.module
            )

            properties(BaseProperties.properties)
        }
    }
}