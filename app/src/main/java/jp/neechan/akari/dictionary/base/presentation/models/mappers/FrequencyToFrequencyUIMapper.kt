package jp.neechan.akari.dictionary.base.presentation.models.mappers

import jp.neechan.akari.dictionary.base.domain.entities.Frequency
import jp.neechan.akari.dictionary.base.domain.entities.mappers.ModelMapper
import jp.neechan.akari.dictionary.base.presentation.models.FrequencyUI

class FrequencyToFrequencyUIMapper : ModelMapper<Frequency, FrequencyUI> {

    override fun mapToInternalLayer(externalLayerModel: FrequencyUI): Frequency {
        return Frequency.valueOf(externalLayerModel.name)
    }

    override fun mapToExternalLayer(internalLayerModel: Frequency): FrequencyUI {
        return FrequencyUI.valueOf(internalLayerModel.name)
    }
}