package jp.neechan.akari.dictionary.common.models.dto

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "Word")
data class WordDto(

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