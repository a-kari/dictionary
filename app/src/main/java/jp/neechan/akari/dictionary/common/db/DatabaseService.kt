package jp.neechan.akari.dictionary.common.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import jp.neechan.akari.dictionary.common.db.dao.WordDao
import jp.neechan.akari.dictionary.common.db.typeconverters.DateTypeConverter
import jp.neechan.akari.dictionary.common.db.typeconverters.FrequencyDtoTypeConverter
import jp.neechan.akari.dictionary.common.db.typeconverters.PartOfSpeechDtoTypeConverter
import jp.neechan.akari.dictionary.common.db.typeconverters.StringListTypeConverter
import jp.neechan.akari.dictionary.common.models.dto.DefinitionDto
import jp.neechan.akari.dictionary.common.models.dto.WordDto

@Database(version = 1, entities = [WordDto::class, DefinitionDto::class])
@TypeConverters(DateTypeConverter::class,
                StringListTypeConverter::class,
                FrequencyDtoTypeConverter::class,
                PartOfSpeechDtoTypeConverter::class)
abstract class DatabaseService : RoomDatabase() {

    abstract fun getWordDao(): WordDao
}