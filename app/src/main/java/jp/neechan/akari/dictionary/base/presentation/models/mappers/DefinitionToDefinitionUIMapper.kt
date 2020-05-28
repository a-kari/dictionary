package jp.neechan.akari.dictionary.base.presentation.models.mappers

import jp.neechan.akari.dictionary.base.domain.entities.Definition
import jp.neechan.akari.dictionary.base.domain.entities.PartOfSpeech
import jp.neechan.akari.dictionary.base.domain.entities.mappers.ModelMapper
import jp.neechan.akari.dictionary.base.presentation.models.DefinitionUI
import jp.neechan.akari.dictionary.base.presentation.models.PartOfSpeechUI

class DefinitionToDefinitionUIMapper(private val partOfSpeechMapper: ModelMapper<PartOfSpeech, PartOfSpeechUI>)
    : ModelMapper<Definition, DefinitionUI> {

    override fun mapToInternalLayer(externalLayerModel: DefinitionUI): Definition {
        return Definition(
            externalLayerModel.id,
            externalLayerModel.wordId,
            externalLayerModel.definition,
            partOfSpeechMapper.mapToInternalLayer(externalLayerModel.partOfSpeech),
            externalLayerModel.examples?.split("\n")?.map { it.removeSurrounding("\"") },
            externalLayerModel.synonyms?.split(", "),
            externalLayerModel.antonyms?.split(", ")
        )
    }

    override fun mapToExternalLayer(internalLayerModel: Definition): DefinitionUI {
        return DefinitionUI(
            internalLayerModel.id,
            internalLayerModel.wordId,
            internalLayerModel.definition,
            partOfSpeechMapper.mapToExternalLayer(internalLayerModel.partOfSpeech),
            internalLayerModel.examples?.joinToString(separator = "\n", transform = { "\"$it\"" }),
            internalLayerModel.synonyms?.joinToString(),
            internalLayerModel.antonyms?.joinToString()
        )
    }
}