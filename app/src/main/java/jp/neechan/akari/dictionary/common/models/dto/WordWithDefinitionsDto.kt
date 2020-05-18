package jp.neechan.akari.dictionary.common.models.dto

import androidx.room.Embedded
import androidx.room.Relation

data class WordWithDefinitionsDto(

    @Embedded
    val word: WordDto,

    @Relation(parentColumn = "word", entityColumn = "wordId")
    val definitions: List<DefinitionDto>?
)