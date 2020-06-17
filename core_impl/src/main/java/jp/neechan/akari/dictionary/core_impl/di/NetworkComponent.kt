package jp.neechan.akari.dictionary.core_impl.di

import dagger.Component
import jp.neechan.akari.dictionary.core_impl.data.framework.network.WordsApi
import jp.neechan.akari.dictionary.core_impl.data.interface_adapters.ResultWrapper
import jp.neechan.akari.dictionary.core_impl.di.qualifiers.RemoteResultWrapper
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkModule::class])
internal interface NetworkComponent {

    fun provideWordsApi(): WordsApi

    @RemoteResultWrapper
    fun provideResultWrapper(): ResultWrapper

    companion object {
        private var networkComponent: NetworkComponent? = null

        fun create(): NetworkComponent {
            return networkComponent
                ?: DaggerNetworkComponent.create().also { networkComponent = it }
        }
    }
}