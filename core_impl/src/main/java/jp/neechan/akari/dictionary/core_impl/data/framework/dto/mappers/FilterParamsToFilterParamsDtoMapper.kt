package jp.neechan.akari.dictionary.core_impl.data.framework.dto.mappers

import dagger.Reusable
import jp.neechan.akari.dictionary.core_api.domain.entities.FilterParams
import jp.neechan.akari.dictionary.core_api.domain.entities.Frequency
import jp.neechan.akari.dictionary.core_api.domain.entities.PartOfSpeech
import jp.neechan.akari.dictionary.core_api.domain.entities.mappers.ModelMapper
import jp.neechan.akari.dictionary.core_impl.data.framework.dto.FilterParamsDto
import jp.neechan.akari.dictionary.core_impl.data.framework.dto.FrequencyDto
import jp.neechan.akari.dictionary.core_impl.data.framework.dto.PartOfSpeechDto
import javax.inject.Inject

@Reusable
internal class FilterParamsToFilterParamsDtoMapper @Inject constructor(
    private val frequencyMapper: ModelMapper<Frequency, FrequencyDto>,
    private val partOfSpeechMapper: ModelMapper<PartOfSpeech, PartOfSpeechDto>) : ModelMapper<FilterParams, FilterParamsDto> {

    override fun mapToInternalLayer(externalLayerModel: FilterParamsDto): FilterParams {
        return FilterParams(
            externalLayerModel.page,
            partOfSpeechMapper.mapToInternalLayer(externalLayerModel.partOfSpeech),
            frequencyMapper.mapToInternalLayer(externalLayerModel.frequency)
        )
    }

    override fun mapToExternalLayer(internalLayerModel: FilterParams): FilterParamsDto {
        return FilterParamsDto(
            internalLayerModel.page,
            partOfSpeechMapper.mapToExternalLayer(internalLayerModel.partOfSpeech),
            frequencyMapper.mapToExternalLayer(internalLayerModel.frequency)
        )
    }
}