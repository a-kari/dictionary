package jp.neechan.akari.dictionary.common.db.typeconverters

import androidx.room.TypeConverter
import java.util.*

object DateTypeConverter {

    @JvmStatic
    @TypeConverter
    fun save(date: Date?): Long? {
        return date?.time
    }

    @JvmStatic
    @TypeConverter
    fun restore(millis: Long?): Date? {
        return millis?.let { Date(millis) }
    }
}