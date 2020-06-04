package jp.neechan.akari.dictionary.base.di

import dagger.Binds
import dagger.Module
import jp.neechan.akari.dictionary.base.data.framework.dto.DefinitionDto
import jp.neechan.akari.dictionary.base.data.framework.dto.FilterParamsDto
import jp.neechan.akari.dictionary.base.data.framework.dto.FrequencyDto
import jp.neechan.akari.dictionary.base.data.framework.dto.PartOfSpeechDto
import jp.neechan.akari.dictionary.base.data.framework.dto.WordDto
import jp.neechan.akari.dictionary.base.data.framework.dto.mappers.DefinitionToDefinitionDtoMapper
import jp.neechan.akari.dictionary.base.data.framework.dto.mappers.FilterParamsToFilterParamsDtoMapper
import jp.neechan.akari.dictionary.base.data.framework.dto.mappers.FrequencyToFrequencyDtoMapper
import jp.neechan.akari.dictionary.base.data.framework.dto.mappers.PartOfSpeechToPartOfSpeechDtoMapper
import jp.neechan.akari.dictionary.base.data.framework.dto.mappers.WordToWordDtoMapper
import jp.neechan.akari.dictionary.base.domain.entities.Definition
import jp.neechan.akari.dictionary.base.domain.entities.FilterParams
import jp.neechan.akari.dictionary.base.domain.entities.Frequency
import jp.neechan.akari.dictionary.base.domain.entities.PartOfSpeech
import jp.neechan.akari.dictionary.base.domain.entities.Word
import jp.neechan.akari.dictionary.base.domain.entities.mappers.ModelMapper

@Module
abstract class DtoMappersModule {

    @Binds
    abstract fun bindWordMapper(
        mapper: WordToWordDtoMapper
    ): ModelMapper<Word, WordDto>

    @Binds
    abstract fun bindDefinitionMapper(
        mapper: DefinitionToDefinitionDtoMapper
    ): ModelMapper<Definition, DefinitionDto>

    @Binds
    abstract fun bindFrequencyMapper(
        mapper: FrequencyToFrequencyDtoMapper
    ): ModelMapper<Frequency, FrequencyDto>

    @Binds
    abstract fun bindPartOfSpeechMapper(
        mapper: PartOfSpeechToPartOfSpeechDtoMapper
    ): ModelMapper<PartOfSpeech, PartOfSpeechDto>

    @Binds
    abstract fun bindFilterParamsMapper(
        mapper: FilterParamsToFilterParamsDtoMapper
    ): ModelMapper<FilterParams, FilterParamsDto>
}