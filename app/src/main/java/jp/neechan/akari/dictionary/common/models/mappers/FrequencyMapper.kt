package jp.neechan.akari.dictionary.common.models.mappers

import jp.neechan.akari.dictionary.common.models.dto.FrequencyDto
import jp.neechan.akari.dictionary.common.models.models.Frequency

class FrequencyMapper : LayerMapper<FrequencyDto, Frequency> {

    override fun mapToHigherLayer(lowerLayerEntity: FrequencyDto): Frequency {
        return Frequency.valueOf(lowerLayerEntity.name)
    }

    override fun mapToLowerLayer(higherLevelEntity: Frequency): FrequencyDto {
        return FrequencyDto.valueOf(higherLevelEntity.name)
    }
}