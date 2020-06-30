package jp.neechan.akari.dictionary.test_utils.robolectric.di

import jp.neechan.akari.dictionary.App
import jp.neechan.akari.dictionary.core_api.di.ProvidersFacade

class TestAppWithFacade : App() {

    override fun getFacade(): ProvidersFacade {
        return TestFacadeComponent.create(this)
    }
}