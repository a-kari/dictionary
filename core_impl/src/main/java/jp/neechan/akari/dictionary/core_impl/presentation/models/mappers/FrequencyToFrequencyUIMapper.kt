package jp.neechan.akari.dictionary.core_impl.presentation.models.mappers

import dagger.Reusable
import jp.neechan.akari.dictionary.core_api.domain.entities.Frequency
import jp.neechan.akari.dictionary.core_api.domain.entities.mappers.ModelMapper
import jp.neechan.akari.dictionary.core_api.presentation.models.FrequencyUI
import javax.inject.Inject

@Reusable
class FrequencyToFrequencyUIMapper @Inject constructor() : ModelMapper<Frequency, FrequencyUI> {

    override fun mapToInternalLayer(externalLayerModel: FrequencyUI): Frequency {
        return Frequency.valueOf(externalLayerModel.name)
    }

    override fun mapToExternalLayer(internalLayerModel: Frequency): FrequencyUI {
        return FrequencyUI.valueOf(internalLayerModel.name)
    }
}