package jp.neechan.akari.dictionary.test_utils.robolectric.di

import android.app.Application
import androidx.room.RoomDatabase
import dagger.Component
import jp.neechan.akari.dictionary.core.di.CoreProvidersFactory
import jp.neechan.akari.dictionary.core_api.di.ContextProvider
import jp.neechan.akari.dictionary.core_api.di.TextToSpeechServiceProvider
import jp.neechan.akari.dictionary.core_api.di.UIMappersProvider
import jp.neechan.akari.dictionary.core_api.di.WordsRepositoryProvider
import jp.neechan.akari.dictionary.core_api.di.scopes.PerApp
import jp.neechan.akari.dictionary.di.ContextComponent
import jp.neechan.akari.dictionary.di.FacadeComponent
import jp.neechan.akari.dictionary.di.MediatorsModule

@PerApp
@Component(
    modules = [MediatorsModule::class],
    dependencies = [
        ContextProvider::class,
        TestDatabaseComponent::class,
        WordsRepositoryProvider::class,
        TextToSpeechServiceProvider::class,
        UIMappersProvider::class
    ]
)
interface TestFacadeComponent : FacadeComponent {

    fun provideDatabase(): RoomDatabase

    companion object {
        private var facadeComponent: FacadeComponent? = null

        fun create(context: Application): FacadeComponent {
            val contextProvider = ContextComponent.create(context)
            return facadeComponent ?:
                DaggerTestFacadeComponent.builder()
                    .contextProvider(contextProvider)
                    .testDatabaseComponent(TestDatabaseComponent.create(contextProvider))
                    .wordsRepositoryProvider(TestWordsRepositoryComponent.create(contextProvider))
                    .textToSpeechServiceProvider(CoreProvidersFactory.createTextToSpeechProvider(contextProvider))
                    .uIMappersProvider(CoreProvidersFactory.createUIMappersProvider())
                    .build()
                    .also { facadeComponent = it }
        }
    }
}