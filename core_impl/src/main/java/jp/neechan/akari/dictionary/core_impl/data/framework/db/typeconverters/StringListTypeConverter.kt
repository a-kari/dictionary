package jp.neechan.akari.dictionary.core_impl.data.framework.db.typeconverters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object StringListTypeConverter {

    private val gson = Gson()

    @JvmStatic
    @TypeConverter
    fun save(list: List<String>?): String? {
        return list?.let { gson.toJson(list) }
    }

    @JvmStatic
    @TypeConverter
    fun restore(jsonArrayString: String?): List<String>? {
        val stringListType = object : TypeToken<List<String>>() {}.type
        return gson.fromJson(jsonArrayString, stringListType)
    }
}