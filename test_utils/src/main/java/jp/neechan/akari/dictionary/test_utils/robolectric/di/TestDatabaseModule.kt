package jp.neechan.akari.dictionary.test_utils.robolectric.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.Reusable
import jp.neechan.akari.dictionary.core_impl.data.framework.db.DatabaseService
import jp.neechan.akari.dictionary.core_impl.data.framework.db.RoomResultWrapper
import jp.neechan.akari.dictionary.core_impl.data.framework.db.dao.WordsDao
import jp.neechan.akari.dictionary.core_impl.data.interface_adapters.ResultWrapper
import jp.neechan.akari.dictionary.core_impl.di.qualifiers.LocalResultWrapper
import javax.inject.Singleton

@Module
internal abstract class TestDatabaseModule {

    companion object {

        @Provides
        @Singleton
        @JvmStatic
        fun provideDatabaseService(context: Context): DatabaseService {
            return Room.inMemoryDatabaseBuilder(context, DatabaseService::class.java)
                       .allowMainThreadQueries()
                       .build()
        }

        @Provides
        @Reusable
        @JvmStatic
        fun provideWordsDao(databaseService: DatabaseService): WordsDao {
            return databaseService.getWordsDao()
        }
    }

    @Binds
    abstract fun bindRoomDatabase(databaseService: DatabaseService): RoomDatabase

    @Binds
    @LocalResultWrapper
    abstract fun bindResultWrapper(roomResultWrapper: RoomResultWrapper): ResultWrapper
}