package jp.neechan.akari.dictionary.core_impl.data.framework.dto.mappers

import dagger.Reusable
import jp.neechan.akari.dictionary.core_api.domain.entities.Definition
import jp.neechan.akari.dictionary.core_api.domain.entities.PartOfSpeech
import jp.neechan.akari.dictionary.core_api.domain.entities.mappers.ModelMapper
import jp.neechan.akari.dictionary.core_impl.data.framework.dto.DefinitionDto
import jp.neechan.akari.dictionary.core_impl.data.framework.dto.PartOfSpeechDto
import javax.inject.Inject

@Reusable
internal class DefinitionToDefinitionDtoMapper @Inject constructor(
    private val partOfSpeechMapper: ModelMapper<PartOfSpeech, PartOfSpeechDto>
) : ModelMapper<Definition, DefinitionDto> {

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
