package jp.neechan.akari.dictionary.base.di

import androidx.room.Room
import jp.neechan.akari.dictionary.base.data.framework.db.DatabaseService
import jp.neechan.akari.dictionary.base.data.framework.db.dao.WordsLocalSourceImpl
import jp.neechan.akari.dictionary.base.data.framework.dto.mappers.WordToWordDtoMapper
import jp.neechan.akari.dictionary.base.data.interface_adapters.WordsLocalSource
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

        single {
            val databaseService: DatabaseService = get()
            databaseService.getWordDao()
        }

        single {
            WordsLocalSourceImpl(get(), get(WordToWordDtoMapper::class)) as WordsLocalSource
        }
    }
}