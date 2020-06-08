package jp.neechan.akari.dictionary.core_impl.data.framework.dto.mappers

import dagger.Reusable
import jp.neechan.akari.dictionary.core_api.domain.entities.PartOfSpeech
import jp.neechan.akari.dictionary.core_api.domain.entities.mappers.ModelMapper
import jp.neechan.akari.dictionary.core_impl.data.framework.dto.PartOfSpeechDto
import javax.inject.Inject

@Reusable
class PartOfSpeechToPartOfSpeechDtoMapper @Inject constructor() : ModelMapper<PartOfSpeech, PartOfSpeechDto> {

    override fun mapToInternalLayer(externalLayerModel: PartOfSpeechDto): PartOfSpeech {
        return PartOfSpeech.valueOf(externalLayerModel.name)
    }

    override fun mapToExternalLayer(internalLayerModel: PartOfSpeech): PartOfSpeechDto {
        return PartOfSpeechDto.valueOf(internalLayerModel.name)
    }
}