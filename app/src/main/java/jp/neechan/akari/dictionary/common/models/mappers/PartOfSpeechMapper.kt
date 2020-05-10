package jp.neechan.akari.dictionary.common.models.mappers

import jp.neechan.akari.dictionary.common.models.dto.PartOfSpeechDto
import jp.neechan.akari.dictionary.common.models.models.PartOfSpeech

class PartOfSpeechMapper : LayerMapper<PartOfSpeechDto, PartOfSpeech> {

    override fun mapToHigherLayer(lowerLayerEntity: PartOfSpeechDto): PartOfSpeech {
        return PartOfSpeech.valueOf(lowerLayerEntity.name)
    }

    override fun mapToLowerLayer(higherLevelEntity: PartOfSpeech): PartOfSpeechDto {
        return PartOfSpeechDto.valueOf(higherLevelEntity.name)
    }
}