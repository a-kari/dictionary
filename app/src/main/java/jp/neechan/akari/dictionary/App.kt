package jp.neechan.akari.dictionary

import android.app.Application
import jp.neechan.akari.dictionary.base.di.AppComponent

class App : Application() {

    companion object {
        private var appComponent: AppComponent? = null
    }

    fun getAppComponent(): AppComponent {
        return appComponent ?: AppComponent.create(this).also { appComponent = it }
    }
}