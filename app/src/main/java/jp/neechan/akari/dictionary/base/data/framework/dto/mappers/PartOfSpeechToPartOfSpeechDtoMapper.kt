package jp.neechan.akari.dictionary.base.data.framework.dto.mappers

import jp.neechan.akari.dictionary.base.data.framework.dto.PartOfSpeechDto
import jp.neechan.akari.dictionary.base.domain.entities.mappers.ModelMapper
import jp.neechan.akari.dictionary.base.domain.entities.PartOfSpeech

class PartOfSpeechToPartOfSpeechDtoMapper : ModelMapper<PartOfSpeech, PartOfSpeechDto> {

    override fun mapToInternalLayer(externalLayerModel: PartOfSpeechDto): PartOfSpeech {
        return PartOfSpeech.valueOf(externalLayerModel.name)
    }

    override fun mapToExternalLayer(internalLayerModel: PartOfSpeech): PartOfSpeechDto {
        return PartOfSpeechDto.valueOf(internalLayerModel.name)
    }
}