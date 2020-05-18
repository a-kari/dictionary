package jp.neechan.akari.dictionary.common.db.typeconverters

import androidx.room.TypeConverter
import jp.neechan.akari.dictionary.common.models.dto.FrequencyDto

object FrequencyDtoTypeConverter {

    @JvmStatic
    @TypeConverter
    fun save(frequencyDto: FrequencyDto): String {
        return frequencyDto.name
    }

    @JvmStatic
    @TypeConverter
    fun restore(frequencyString: String): FrequencyDto {
        return FrequencyDto.valueOf(frequencyString)
    }
}