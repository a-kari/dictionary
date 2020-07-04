package jp.neechan.akari.dictionary.core_impl.data.framework.db.typeconverters

import androidx.room.TypeConverter
import jp.neechan.akari.dictionary.core_impl.data.framework.dto.FrequencyDto

internal class FrequencyDtoTypeConverter : AbstractTypeConverter<FrequencyDto, String> {

    @TypeConverter
    override fun save(restored: FrequencyDto): String {
        return restored.name
    }

    @TypeConverter
    override fun restore(saved: String): FrequencyDto {
        return FrequencyDto.valueOf(saved)
    }
}
