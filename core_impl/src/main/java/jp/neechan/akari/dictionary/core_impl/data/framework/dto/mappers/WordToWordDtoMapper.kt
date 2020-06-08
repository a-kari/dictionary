package jp.neechan.akari.dictionary.core_impl.data.framework.dto.mappers

import dagger.Reusable
import jp.neechan.akari.dictionary.core_api.domain.entities.Definition
import jp.neechan.akari.dictionary.core_api.domain.entities.Frequency
import jp.neechan.akari.dictionary.core_api.domain.entities.Word
import jp.neechan.akari.dictionary.core_api.domain.entities.mappers.ModelMapper
import jp.neechan.akari.dictionary.core_impl.data.framework.dto.DefinitionDto
import jp.neechan.akari.dictionary.core_impl.data.framework.dto.FrequencyDto
import jp.neechan.akari.dictionary.core_impl.data.framework.dto.WordDto
import javax.inject.Inject

@Reusable
class WordToWordDtoMapper @Inject constructor(
    private val frequencyMapper: ModelMapper<Frequency, FrequencyDto>,
    private val definitionMapper: ModelMapper<Definition, DefinitionDto>) : ModelMapper<Word, WordDto> {

    override fun mapToInternalLayer(externalLayerModel: WordDto): Word {
        return Word(
            externalLayerModel.word,
            externalLayerModel.pronunciation,
            externalLayerModel.syllables,
            frequencyMapper.mapToInternalLayer(externalLayerModel.frequency),
            externalLayerModel.definitions?.map { definitionMapper.mapToInternalLayer(it) },
            externalLayerModel.saveDate
        )
    }

    override fun mapToExternalLayer(internalLayerModel: Word): WordDto {
        return WordDto(
            internalLayerModel.word,
            internalLayerModel.pronunciation,
            internalLayerModel.syllables,
            frequencyMapper.mapToExternalLayer(internalLayerModel.frequency),
            internalLayerModel.definitions?.map { definitionMapper.mapToExternalLayer(it) },
            internalLayerModel.saveDate
        )
    }
}