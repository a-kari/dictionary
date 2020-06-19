package jp.neechan.akari.dictionary.core_impl.data.framework.dto

import androidx.room.Embedded
import androidx.room.Relation

internal data class WordWithDefinitionsDto(

    @Embedded
    val word: WordDto,

    @Relation(parentColumn = "word", entityColumn = "wordId")
    val definitions: List<DefinitionDto>?
)