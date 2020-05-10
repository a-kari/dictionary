package jp.neechan.akari.dictionary.common.models.mappers

import jp.neechan.akari.dictionary.common.models.dto.WordDto
import jp.neechan.akari.dictionary.common.models.models.Definition
import jp.neechan.akari.dictionary.common.models.models.PartOfSpeech
import jp.neechan.akari.dictionary.common.models.models.Word

class WordMapper(private val definitionMapper: DefinitionMapper,
                 private val frequencyMapper: FrequencyMapper,
                 private val partOfSpeechMapper: PartOfSpeechMapper) : LayerMapper<WordDto, Word> {

    override fun mapToHigherLayer(lowerLayerEntity: WordDto): Word {
        var definitions: LinkedHashMap<PartOfSpeech, List<Definition>>? = null
        if (lowerLayerEntity.definitions != null) {
            definitions = LinkedHashMap()
            for (definitionDto in lowerLayerEntity.definitions) {
                val partOfSpeech = partOfSpeechMapper.mapToHigherLayer(definitionDto.partOfSpeech)
                val partOfSpeechDefinitions = definitions[partOfSpeech] as MutableList<Definition>? ?: mutableListOf()
                partOfSpeechDefinitions.add(definitionMapper.mapToHigherLayer(definitionDto))
                definitions[partOfSpeech] = partOfSpeechDefinitions
            }
        }

        return Word(
            lowerLayerEntity.word,
            lowerLayerEntity.pronunciation,
            lowerLayerEntity.syllables?.joinToString(separator = "-"),
            frequencyMapper.mapToHigherLayer(lowerLayerEntity.frequency),
            definitions
        )
    }

    override fun mapToLowerLayer(higherLevelEntity: Word): WordDto {
        return WordDto(
            higherLevelEntity.word,
            higherLevelEntity.pronunciation,
            higherLevelEntity.syllables?.split("-"),
            frequencyMapper.mapToLowerLayer(higherLevelEntity.frequency),
            higherLevelEntity.definitions?.values?.flatten()?.map { definitionMapper.mapToLowerLayer(it) }
        )
    }
}