package jp.neechan.akari.dictionary.core_impl.data.framework.dto.mappers

import dagger.Reusable
import jp.neechan.akari.dictionary.core_api.domain.entities.Frequency
import jp.neechan.akari.dictionary.core_api.domain.entities.mappers.ModelMapper
import jp.neechan.akari.dictionary.core_impl.data.framework.dto.FrequencyDto
import javax.inject.Inject

@Reusable
internal class FrequencyToFrequencyDtoMapper @Inject constructor() : ModelMapper<Frequency, FrequencyDto> {

    override fun mapToInternalLayer(externalLayerModel: FrequencyDto): Frequency {
        return Frequency.valueOf(externalLayerModel.name)
    }

    override fun mapToExternalLayer(internalLayerModel: Frequency): FrequencyDto {
        return FrequencyDto.valueOf(internalLayerModel.name)
    }
}