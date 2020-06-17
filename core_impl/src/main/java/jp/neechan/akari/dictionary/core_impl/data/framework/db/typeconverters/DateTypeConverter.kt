package jp.neechan.akari.dictionary.core_impl.data.framework.db.typeconverters

import androidx.room.TypeConverter
import java.util.Date

internal class DateTypeConverter : AbstractTypeConverter<Date?, Long?> {

    @TypeConverter
    override fun save(restored: Date?): Long? {
        return restored?.time
    }

    @TypeConverter
    override fun restore(saved: Long?): Date? {
        return saved?.let { Date(saved) }
    }
}