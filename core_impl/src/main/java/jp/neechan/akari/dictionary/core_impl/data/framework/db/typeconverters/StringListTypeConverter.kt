package jp.neechan.akari.dictionary.core_impl.data.framework.db.typeconverters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

internal class StringListTypeConverter : AbstractTypeConverter<List<String>?, String?> {

    private val gson = Gson()

    @TypeConverter
    override fun save(restored: List<String>?): String? {
        return restored?.let { gson.toJson(restored) }
    }

    @TypeConverter
    override fun restore(saved: String?): List<String>? {
        val stringListType = object : TypeToken<List<String>>() {}.type
        return gson.fromJson(saved, stringListType)
    }
}