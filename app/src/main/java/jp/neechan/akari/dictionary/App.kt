package jp.neechan.akari.dictionary

import android.app.Application
import jp.neechan.akari.dictionary.core_api.di.AppWithFacade
import jp.neechan.akari.dictionary.core_api.di.ProvidersFacade
import jp.neechan.akari.dictionary.di.FacadeComponent

class App : Application(), AppWithFacade {

    override fun getFacade(): ProvidersFacade {
        return FacadeComponent.create(this)
    }
}