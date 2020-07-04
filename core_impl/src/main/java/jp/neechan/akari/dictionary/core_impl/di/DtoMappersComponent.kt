package jp.neechan.akari.dictionary.core_impl.di

import dagger.Component
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
import javax.inject.Singleton

@Singleton
@Component(modules = [DtoMappersModule::class])
internal interface DtoMappersComponent {

    fun provideWordMapper(): ModelMapper<Word, WordDto>

    fun provideDefinitionMapper(): ModelMapper<Definition, DefinitionDto>

    fun provideFrequencyMapper(): ModelMapper<Frequency, FrequencyDto>

    fun providePartOfSpeechMapper(): ModelMapper<PartOfSpeech, PartOfSpeechDto>

    fun provideFilterParamsMapper(): ModelMapper<FilterParams, FilterParamsDto>

    companion object {
        private var dtoMappersComponent: DtoMappersComponent? = null

        fun create(): DtoMappersComponent {
            return dtoMappersComponent ?: DaggerDtoMappersComponent.create().also { dtoMappersComponent = it }
        }
    }
}
