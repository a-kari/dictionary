package jp.neechan.akari.dictionary.core_impl.presentation.models.mappers

import dagger.Reusable
import jp.neechan.akari.dictionary.core_api.domain.entities.Definition
import jp.neechan.akari.dictionary.core_api.domain.entities.Frequency
import jp.neechan.akari.dictionary.core_api.domain.entities.PartOfSpeech
import jp.neechan.akari.dictionary.core_api.domain.entities.Word
import jp.neechan.akari.dictionary.core_api.domain.entities.mappers.ModelMapper
import jp.neechan.akari.dictionary.core_api.presentation.models.DefinitionUI
import jp.neechan.akari.dictionary.core_api.presentation.models.FrequencyUI
import jp.neechan.akari.dictionary.core_api.presentation.models.PartOfSpeechUI
import jp.neechan.akari.dictionary.core_api.presentation.models.WordUI
import javax.inject.Inject

@Reusable
internal class WordToWordUIMapper @Inject constructor(
    private val frequencyMapper: ModelMapper<Frequency, FrequencyUI>,
    private val partOfSpeechMapper: ModelMapper<PartOfSpeech, PartOfSpeechUI>,
    private val definitionMapper: ModelMapper<Definition, DefinitionUI>
) : ModelMapper<Word, WordUI> {

    override fun mapToInternalLayer(externalLayerModel: WordUI): Word {
        return Word(
            externalLayerModel.word,
            externalLayerModel.pronunciation,
            externalLayerModel.syllables?.split("-"),
            frequencyMapper.mapToInternalLayer(externalLayerModel.frequency),
            externalLayerModel.definitions?.values?.flatten()?.map { definitionMapper.mapToInternalLayer(it) },
            externalLayerModel.saveDate
        )
    }

    override fun mapToExternalLayer(internalLayerModel: Word): WordUI {
        var definitions: LinkedHashMap<PartOfSpeechUI, List<DefinitionUI>>? = null
        if (internalLayerModel.definitions != null) {
            definitions = LinkedHashMap()
            for (definition in internalLayerModel.definitions!!) {
                val partOfSpeech = partOfSpeechMapper.mapToExternalLayer(definition.partOfSpeech)
                val partOfSpeechDefinitions = definitions[partOfSpeech] as MutableList<DefinitionUI>? ?: mutableListOf()
                partOfSpeechDefinitions.add(definitionMapper.mapToExternalLayer(definition))
                definitions[partOfSpeech] = partOfSpeechDefinitions
            }
        }

        return WordUI(
            internalLayerModel.word,
            internalLayerModel.pronunciation,
            internalLayerModel.syllables?.joinToString(separator = "-"),
            frequencyMapper.mapToExternalLayer(internalLayerModel.frequency),
            definitions,
            internalLayerModel.saveDate
        )
    }
}