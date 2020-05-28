package jp.neechan.akari.dictionary.base.data.framework.dto.mappers

import jp.neechan.akari.dictionary.base.data.framework.dto.DefinitionDto
import jp.neechan.akari.dictionary.base.data.framework.dto.FrequencyDto
import jp.neechan.akari.dictionary.base.data.framework.dto.WordDto
import jp.neechan.akari.dictionary.base.domain.entities.Definition
import jp.neechan.akari.dictionary.base.domain.entities.Frequency
import jp.neechan.akari.dictionary.base.domain.entities.Word
import jp.neechan.akari.dictionary.base.domain.entities.mappers.ModelMapper

class WordToWordDtoMapper(private val frequencyMapper: ModelMapper<Frequency, FrequencyDto>,
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