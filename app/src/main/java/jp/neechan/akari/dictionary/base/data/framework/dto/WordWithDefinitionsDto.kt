package jp.neechan.akari.dictionary.base.data.framework.dto

import androidx.room.Embedded
import androidx.room.Relation

data class WordWithDefinitionsDto(

    @Embedded
    val word: WordDto,

    @Relation(parentColumn = "word", entityColumn = "wordId")
    val definitions: List<DefinitionDto>?
)