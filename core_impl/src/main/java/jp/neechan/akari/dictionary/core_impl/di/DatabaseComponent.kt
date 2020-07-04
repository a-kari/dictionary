package jp.neechan.akari.dictionary.core_impl.di

import dagger.Component
import jp.neechan.akari.dictionary.core_api.di.ContextProvider
import jp.neechan.akari.dictionary.core_impl.data.framework.db.dao.WordsDao
import jp.neechan.akari.dictionary.core_impl.data.interface_adapters.ResultWrapper
import jp.neechan.akari.dictionary.core_impl.di.qualifiers.LocalResultWrapper
import javax.inject.Singleton

@Singleton
@Component(modules = [DatabaseModule::class], dependencies = [ContextProvider::class])
internal interface DatabaseComponent {

    fun provideWordsDao(): WordsDao

    @LocalResultWrapper
    fun provideResultWrapper(): ResultWrapper

    companion object {
        private var databaseComponent: DatabaseComponent? = null

        fun create(contextProvider: ContextProvider): DatabaseComponent {
            return databaseComponent
                ?: DaggerDatabaseComponent.builder()
                                          .contextProvider(contextProvider)
                                          .build()
                                          .also { databaseComponent = it }
        }
    }
}
