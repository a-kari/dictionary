package jp.neechan.akari.dictionary.test_utils.robolectric.di

import dagger.Component
import jp.neechan.akari.dictionary.core_api.di.ContextProvider
import jp.neechan.akari.dictionary.core_api.di.scopes.PerApp
import jp.neechan.akari.dictionary.core_impl.di.DatabaseComponent
import jp.neechan.akari.dictionary.core_impl.di.DtoMappersComponent
import jp.neechan.akari.dictionary.core_impl.di.NetworkComponent
import jp.neechan.akari.dictionary.core_impl.di.SharedPreferencesComponent
import jp.neechan.akari.dictionary.core_impl.di.WordsRepositoryComponent
import jp.neechan.akari.dictionary.core_impl.di.WordsRepositoryModule

@PerApp
@Component(
    modules = [WordsRepositoryModule::class],
    dependencies = [
        TestDatabaseComponent::class,
        TestNetworkComponent::class,
        SharedPreferencesComponent::class,
        DtoMappersComponent::class
    ]
)
interface TestWordsRepositoryComponent : WordsRepositoryComponent {

    companion object {
        private var wordsRepositoryComponent: TestWordsRepositoryComponent? = null

        fun create(contextProvider: ContextProvider): TestWordsRepositoryComponent {
            return wordsRepositoryComponent
                ?: DaggerTestWordsRepositoryComponent.builder()
                    .testDatabaseComponent(TestDatabaseComponent.create(contextProvider))
                    .testNetworkComponent(TestNetworkComponent.create())
                    .sharedPreferencesComponent(SharedPreferencesComponent.create(contextProvider))
                    .dtoMappersComponent(DtoMappersComponent.create())
                    .build()
                    .also { wordsRepositoryComponent = it }

        }
    }
}