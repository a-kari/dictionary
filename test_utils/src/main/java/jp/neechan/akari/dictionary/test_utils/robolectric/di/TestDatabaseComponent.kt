package jp.neechan.akari.dictionary.test_utils.robolectric.di

import androidx.room.RoomDatabase
import dagger.Component
import jp.neechan.akari.dictionary.core_api.di.ContextProvider
import jp.neechan.akari.dictionary.core_impl.di.DatabaseComponent
import javax.inject.Singleton

@Singleton
@Component(modules = [TestDatabaseModule::class], dependencies = [ContextProvider::class])
interface TestDatabaseComponent : DatabaseComponent {

    fun provideDatabase(): RoomDatabase

    companion object {
        private var databaseComponent: TestDatabaseComponent? = null

        fun create(contextProvider: ContextProvider): TestDatabaseComponent {
            return databaseComponent
                ?: DaggerTestDatabaseComponent.builder()
                                              .contextProvider(contextProvider)
                                              .build()
                                              .also { databaseComponent = it }
        }
    }
}