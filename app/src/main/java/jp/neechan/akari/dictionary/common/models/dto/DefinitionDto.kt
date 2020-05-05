package jp.neechan.akari.dictionary.common.models.dto

data class DefinitionDto(
    val definition: String,
    val partOfSpeech: PartOfSpeechDto,
    val examples: List<String>?,
    val synonyms: List<String>?,
    val antonyms: List<String>?
)
