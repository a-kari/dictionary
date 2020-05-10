package jp.neechan.akari.dictionary.common.models.mappers

import jp.neechan.akari.dictionary.common.models.dto.DefinitionDto
import jp.neechan.akari.dictionary.common.models.models.Definition

class DefinitionMapper(private val partOfSpeechMapper: PartOfSpeechMapper) : LayerMapper<DefinitionDto, Definition> {

    override fun mapToHigherLayer(lowerLayerEntity: DefinitionDto): Definition {
        return Definition(
            lowerLayerEntity.definition,
            partOfSpeechMapper.mapToHigherLayer(lowerLayerEntity.partOfSpeech),
            lowerLayerEntity.examples?.joinToString(separator = "\n", transform = { "\"$it\"" }),
            lowerLayerEntity.synonyms?.joinToString(),
            lowerLayerEntity.antonyms?.joinToString()
        )
    }

    override fun mapToLowerLayer(higherLevelEntity: Definition): DefinitionDto {
        return DefinitionDto(
            higherLevelEntity.definition,
            partOfSpeechMapper.mapToLowerLayer(higherLevelEntity.partOfSpeech),
            higherLevelEntity.examples?.split("\n")?.map { it.removeSurrounding("\"") },
            higherLevelEntity.synonyms?.split(", "),
            higherLevelEntity.antonyms?.split(", ")
        )
    }
}