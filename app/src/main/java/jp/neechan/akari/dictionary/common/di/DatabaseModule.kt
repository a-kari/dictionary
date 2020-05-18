package jp.neechan.akari.dictionary.common.di

import androidx.room.Room
import jp.neechan.akari.dictionary.common.db.DatabaseService
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

object DatabaseModule : KoinModule {

    override fun get() = module {
        single {
            Room.databaseBuilder(
                androidApplication(),
                DatabaseService::class.java,
                "dictionary.db"
            ).build()
        }
    }
}