package jp.neechan.akari.dictionary.base.data.framework.dto.mappers

import jp.neechan.akari.dictionary.base.data.framework.dto.DefinitionDto
import jp.neechan.akari.dictionary.base.data.framework.dto.PartOfSpeechDto
import jp.neechan.akari.dictionary.base.domain.entities.Definition
import jp.neechan.akari.dictionary.base.domain.entities.mappers.ModelMapper
import jp.neechan.akari.dictionary.base.domain.entities.PartOfSpeech

class DefinitionToDefinitionDtoMapper(private val partOfSpeechMapper: ModelMapper<PartOfSpeech, PartOfSpeechDto>)
    : ModelMapper<Definition, DefinitionDto> {

    override fun mapToInternalLayer(externalLayerModel: DefinitionDto): Definition {
        return Definition(
            externalLayerModel.id,
            externalLayerModel.wordId,
            externalLayerModel.definition,
            partOfSpeechMapper.mapToInternalLayer(externalLayerModel.partOfSpeech),
            externalLayerModel.examples,
            externalLayerModel.synonyms,
            externalLayerModel.antonyms
        )
    }

    override fun mapToExternalLayer(internalLayerModel: Definition): DefinitionDto {
        return DefinitionDto(
            internalLayerModel.id,
            internalLayerModel.wordId,
            internalLayerModel.definition,
            partOfSpeechMapper.mapToExternalLayer(internalLayerModel.partOfSpeech),
            internalLayerModel.examples,
            internalLayerModel.synonyms,
            internalLayerModel.antonyms
        )
    }
}