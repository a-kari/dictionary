package jp.neechan.akari.dictionary.core_impl.di

import dagger.Component
import jp.neechan.akari.dictionary.core_api.di.ContextProvider
import jp.neechan.akari.dictionary.core_api.di.WordsRepositoryProvider
import jp.neechan.akari.dictionary.core_api.di.scopes.PerApp

@PerApp
@Component(
    modules = [WordsRepositoryModule::class],
    dependencies = [
        DatabaseComponent::class,
        NetworkComponent::class,
        SharedPreferencesComponent::class,
        DtoMappersComponent::class
    ]
)
interface WordsRepositoryComponent : WordsRepositoryProvider {

    companion object {
        private var wordsRepositoryComponent: WordsRepositoryComponent? = null

        fun create(contextProvider: ContextProvider): WordsRepositoryComponent {
            return wordsRepositoryComponent
                ?: DaggerWordsRepositoryComponent.builder()
                    .databaseComponent(DatabaseComponent.create(contextProvider))
                    .networkComponent(NetworkComponent.create())
                    .sharedPreferencesComponent(SharedPreferencesComponent.create(contextProvider))
                    .dtoMappersComponent(DtoMappersComponent.create())
                    .build()
                    .also { wordsRepositoryComponent = it }

        }
    }
}