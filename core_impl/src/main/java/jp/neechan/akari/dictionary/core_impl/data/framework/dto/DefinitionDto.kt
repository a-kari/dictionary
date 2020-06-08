package jp.neechan.akari.dictionary.core_impl.data.framework.dto

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "Definition",
    foreignKeys = [ForeignKey(
        entity = WordDto::class,
        parentColumns = ["word"],
        childColumns = ["wordId"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class DefinitionDto(

    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    val wordId: String,

    val definition: String,

    val partOfSpeech: PartOfSpeechDto,

    val examples: List<String>?,

    val synonyms: List<String>?,

    val antonyms: List<String>?
) {
    constructor(
        wordId: String,
        definition: String,
        partOfSpeech: PartOfSpeechDto,
        examples: List<String>?,
        synonyms: List<String>?,
        antonyms: List<String>?
    ) : this(0, wordId, definition, partOfSpeech, examples, synonyms, antonyms)
}
