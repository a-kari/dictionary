package jp.neechan.akari.dictionary.core_impl.di

import dagger.Binds
import dagger.Module
import jp.neechan.akari.dictionary.core_api.domain.entities.Definition
import jp.neechan.akari.dictionary.core_api.domain.entities.FilterParams
import jp.neechan.akari.dictionary.core_api.domain.entities.Frequency
import jp.neechan.akari.dictionary.core_api.domain.entities.PartOfSpeech
import jp.neechan.akari.dictionary.core_api.domain.entities.Word
import jp.neechan.akari.dictionary.core_api.domain.entities.mappers.ModelMapper
import jp.neechan.akari.dictionary.core_impl.data.framework.dto.DefinitionDto
import jp.neechan.akari.dictionary.core_impl.data.framework.dto.FilterParamsDto
import jp.neechan.akari.dictionary.core_impl.data.framework.dto.FrequencyDto
import jp.neechan.akari.dictionary.core_impl.data.framework.dto.PartOfSpeechDto
import jp.neechan.akari.dictionary.core_impl.data.framework.dto.WordDto
import jp.neechan.akari.dictionary.core_impl.data.framework.dto.mappers.DefinitionToDefinitionDtoMapper
import jp.neechan.akari.dictionary.core_impl.data.framework.dto.mappers.FilterParamsToFilterParamsDtoMapper
import jp.neechan.akari.dictionary.core_impl.data.framework.dto.mappers.FrequencyToFrequencyDtoMapper
import jp.neechan.akari.dictionary.core_impl.data.framework.dto.mappers.PartOfSpeechToPartOfSpeechDtoMapper
import jp.neechan.akari.dictionary.core_impl.data.framework.dto.mappers.WordToWordDtoMapper

@Module
internal abstract class DtoMappersModule {

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