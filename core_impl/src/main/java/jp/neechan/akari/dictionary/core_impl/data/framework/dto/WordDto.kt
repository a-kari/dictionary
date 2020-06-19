package jp.neechan.akari.dictionary.core_impl.data.framework.dto

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "Word")
internal data class WordDto(

    @PrimaryKey
    val word: String,

    val pronunciation: String?,

    val syllables: List<String>?,

    val frequency: FrequencyDto,

    @Ignore
    var definitions: List<DefinitionDto>?,

    var saveDate: Date? = null // Save to the db date.
) {

    // Constructor for Room.
    constructor(
        word: String,
        pronunciation: String?,
        syllables: List<String>?,
        frequency: FrequencyDto,
        saveDate: Date?
    ) : this(word, pronunciation, syllables, frequency, null, saveDate)
}