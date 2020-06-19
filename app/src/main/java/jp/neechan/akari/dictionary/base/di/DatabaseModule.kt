package jp.neechan.akari.dictionary.base.di

import android.content.Context
import androidx.room.Room
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.Reusable
import jp.neechan.akari.dictionary.base.data.framework.db.DatabaseService
import jp.neechan.akari.dictionary.base.data.framework.db.dao.WordsDao
import jp.neechan.akari.dictionary.base.data.framework.db.dao.WordsLocalSourceImpl
import jp.neechan.akari.dictionary.base.data.interface_adapters.WordsLocalSource
import jp.neechan.akari.dictionary.base.di.qualifiers.DatabaseName
import javax.inject.Singleton

@Module
abstract class DatabaseModule {

    @Module
    companion object {

        @Provides
        @Reusable
        @DatabaseName
        @JvmStatic
        fun provideDatabaseName() = "dictionary.db"

        @Provides
        @Singleton
        @JvmStatic
        fun provideDatabaseService(context: Context, @DatabaseName databaseName: String): DatabaseService {
            return Room.databaseBuilder(context, DatabaseService::class.java, databaseName).build()
        }

        @Provides
        @Reusable
        @JvmStatic
        fun provideWordsDao(databaseService: DatabaseService): WordsDao {
            return databaseService.getWordsDao()
        }
    }

    @Binds
    abstract fun bindWordsLocalSource(wordsLocalSourceImpl: WordsLocalSourceImpl): WordsLocalSource
}