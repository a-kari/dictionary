package jp.neechan.akari.dictionary.base.data.framework.dto.mappers

import jp.neechan.akari.dictionary.base.data.framework.dto.FrequencyDto
import jp.neechan.akari.dictionary.base.domain.entities.Frequency
import jp.neechan.akari.dictionary.base.domain.entities.mappers.ModelMapper

class FrequencyToFrequencyDtoMapper : ModelMapper<Frequency, FrequencyDto> {

    override fun mapToInternalLayer(externalLayerModel: FrequencyDto): Frequency {
        return Frequency.valueOf(externalLayerModel.name)
    }

    override fun mapToExternalLayer(internalLayerModel: Frequency): FrequencyDto {
        return FrequencyDto.valueOf(internalLayerModel.name)
    }
}