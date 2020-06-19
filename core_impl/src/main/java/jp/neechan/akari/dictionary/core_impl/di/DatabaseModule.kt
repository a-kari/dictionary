package jp.neechan.akari.dictionary.core_impl.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.Reusable
import jp.neechan.akari.dictionary.core_impl.data.framework.db.DatabaseService
import jp.neechan.akari.dictionary.core_impl.data.framework.db.dao.WordsDao
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
internal object DatabaseModule {

    @Qualifier
    @MustBeDocumented
    @Retention(AnnotationRetention.RUNTIME)
    internal annotation class DatabaseName

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