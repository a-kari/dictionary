package jp.neechan.akari.dictionary.base.data.framework.db.typeconverters

import androidx.room.TypeConverter
import jp.neechan.akari.dictionary.base.data.framework.dto.PartOfSpeechDto

object PartOfSpeechDtoTypeConverter {

    @JvmStatic
    @TypeConverter
    fun save(partOfSpeech: PartOfSpeechDto): String {
        return partOfSpeech.name
    }

    @JvmStatic
    @TypeConverter
    fun restore(partOfSpeechString: String): PartOfSpeechDto {
        return PartOfSpeechDto.valueOf(partOfSpeechString)
    }
}