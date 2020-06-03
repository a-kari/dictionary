package jp.neechan.akari.dictionary.base.presentation.models.mappers

import jp.neechan.akari.dictionary.base.domain.entities.PartOfSpeech
import jp.neechan.akari.dictionary.base.domain.entities.mappers.ModelMapper
import jp.neechan.akari.dictionary.base.presentation.models.PartOfSpeechUI

class PartOfSpeechToPartOfSpeechUIMapper : ModelMapper<PartOfSpeech, PartOfSpeechUI> {

    override fun mapToInternalLayer(externalLayerModel: PartOfSpeechUI): PartOfSpeech {
        return PartOfSpeech.valueOf(externalLayerModel.name)
    }

    override fun mapToExternalLayer(internalLayerModel: PartOfSpeech): PartOfSpeechUI {
        return PartOfSpeechUI.valueOf(internalLayerModel.name)
    }
}