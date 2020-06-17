package jp.neechan.akari.dictionary.core_impl.data.framework.db.typeconverters

import androidx.room.TypeConverter
import jp.neechan.akari.dictionary.core_impl.data.framework.dto.PartOfSpeechDto

internal class PartOfSpeechDtoTypeConverter : AbstractTypeConverter<PartOfSpeechDto, String> {

    @TypeConverter
    override fun save(restored: PartOfSpeechDto): String {
        return restored.name
    }

    @TypeConverter
    override fun restore(saved: String): PartOfSpeechDto {
        return PartOfSpeechDto.valueOf(saved)
    }
}