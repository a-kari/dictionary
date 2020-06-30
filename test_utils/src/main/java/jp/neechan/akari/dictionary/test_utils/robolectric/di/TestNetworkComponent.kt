package jp.neechan.akari.dictionary.test_utils.robolectric.di

import dagger.Component
import jp.neechan.akari.dictionary.core_impl.di.NetworkComponent
import javax.inject.Singleton

@Singleton
@Component(modules = [TestNetworkModule::class])
internal interface TestNetworkComponent : NetworkComponent {

    companion object {
        private var networkComponent: TestNetworkComponent? = null

        fun create(): TestNetworkComponent {
            return networkComponent
                ?: DaggerTestNetworkComponent.create().also { networkComponent = it }
        }
    }
}