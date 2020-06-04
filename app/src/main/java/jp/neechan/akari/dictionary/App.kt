package jp.neechan.akari.dictionary

import android.app.Application
import jp.neechan.akari.dictionary.base.di.AppComponent
import jp.neechan.akari.dictionary.base.di.DaggerAppComponent

class App : Application() {

    companion object {
        private var appComponent: AppComponent? = null
    }

    fun getAppComponent(): AppComponent {
        return appComponent ?:
               DaggerAppComponent.builder().context(this).build().also { appComponent = it }
    }
}