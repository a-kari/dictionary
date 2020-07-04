package jp.neechan.akari.dictionary.core_impl.presentation.models.mappers

import dagger.Reusable
import jp.neechan.akari.dictionary.core_api.domain.entities.PartOfSpeech
import jp.neechan.akari.dictionary.core_api.domain.entities.mappers.ModelMapper
import jp.neechan.akari.dictionary.core_api.presentation.models.PartOfSpeechUI
import javax.inject.Inject

@Reusable
internal class PartOfSpeechToPartOfSpeechUIMapper @Inject constructor() :
    ModelMapper<PartOfSpeech, PartOfSpeechUI> {

    override fun mapToInternalLayer(externalLayerModel: PartOfSpeechUI): PartOfSpeech {
        return PartOfSpeech.valueOf(externalLayerModel.name)
    }

    override fun mapToExternalLayer(internalLayerModel: PartOfSpeech): PartOfSpeechUI {
        return PartOfSpeechUI.valueOf(internalLayerModel.name)
    }
}
