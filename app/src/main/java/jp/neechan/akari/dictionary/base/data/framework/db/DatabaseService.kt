package jp.neechan.akari.dictionary.base.data.framework.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import jp.neechan.akari.dictionary.base.data.framework.db.dao.WordsDao
import jp.neechan.akari.dictionary.base.data.framework.db.typeconverters.DateTypeConverter
import jp.neechan.akari.dictionary.base.data.framework.db.typeconverters.FrequencyDtoTypeConverter
import jp.neechan.akari.dictionary.base.data.framework.db.typeconverters.PartOfSpeechDtoTypeConverter
import jp.neechan.akari.dictionary.base.data.framework.db.typeconverters.StringListTypeConverter
import jp.neechan.akari.dictionary.base.data.framework.dto.DefinitionDto
import jp.neechan.akari.dictionary.base.data.framework.dto.WordDto

@Database(version = 1, entities = [WordDto::class, DefinitionDto::class])
@TypeConverters(DateTypeConverter::class,
                StringListTypeConverter::class,
                FrequencyDtoTypeConverter::class,
                PartOfSpeechDtoTypeConverter::class)
abstract class DatabaseService : RoomDatabase() {

    abstract fun getWordsDao(): WordsDao
}