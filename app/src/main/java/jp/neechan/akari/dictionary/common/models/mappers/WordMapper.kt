package jp.neechan.akari.dictionary.common.models.mappers

import jp.neechan.akari.dictionary.common.models.dto.WordDto
import jp.neechan.akari.dictionary.common.models.models.Definition
import jp.neechan.akari.dictionary.common.models.models.PartOfSpeech
import jp.neechan.akari.dictionary.common.models.models.Word

class WordMapper(
    private val definitionMapper: DefinitionMapper,
    private val frequencyMapper: FrequencyMapper,
    private val partOfSpeechMapper: PartOfSpeechMapper
) : DtoToModelMapper<WordDto, Word> {

    override fun toModel(dto: WordDto): Word {
        var definitions: LinkedHashMap<PartOfSpeech, List<Definition>>? = null
        if (dto.definitions != null) {
            definitions = LinkedHashMap()
            for (definitionDto in dto.definitions) {
                val partOfSpeech = partOfSpeechMapper.toModel(definitionDto.partOfSpeech)
                val partOfSpeechDefinitions = definitions[partOfSpeech] as MutableList<Definition>? ?: mutableListOf()
                partOfSpeechDefinitions.add(definitionMapper.toModel(definitionDto))
                definitions[partOfSpeech] = partOfSpeechDefinitions
            }
        }

        return Word(
            dto.word,
            dto.pronunciation,
            dto.syllables?.joinToString(separator = "-"),
            frequencyMapper.toModel(dto.frequency),
            definitions
        )
    }
}