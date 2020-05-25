package jp.neechan.akari.dictionary.base.data.framework.db.typeconverters

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