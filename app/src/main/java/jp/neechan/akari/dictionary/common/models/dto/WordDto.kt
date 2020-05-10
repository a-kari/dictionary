package jp.neechan.akari.dictionary.common.models.dto

data class WordDto(val word: String,
                   val pronunciation: String?,
                   val syllables: List<String>?,
                   val frequency: FrequencyDto,
                   val definitions: List<DefinitionDto>?)