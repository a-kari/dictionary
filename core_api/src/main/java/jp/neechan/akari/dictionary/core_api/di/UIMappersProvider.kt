package jp.neechan.akari.dictionary.core_api.di

import jp.neechan.akari.dictionary.core_api.domain.entities.Frequency
import jp.neechan.akari.dictionary.core_api.domain.entities.Page
import jp.neechan.akari.dictionary.core_api.domain.entities.PartOfSpeech
import jp.neechan.akari.dictionary.core_api.domain.entities.Result
import jp.neechan.akari.dictionary.core_api.domain.entities.Word
import jp.neechan.akari.dictionary.core_api.domain.entities.mappers.ModelMapper
import jp.neechan.akari.dictionary.core_api.presentation.models.FrequencyUI
import jp.neechan.akari.dictionary.core_api.presentation.models.PartOfSpeechUI
import jp.neechan.akari.dictionary.core_api.presentation.models.UIState
import jp.neechan.akari.dictionary.core_api.presentation.models.WordUI

interface UIMappersProvider {

    fun provideWordUIMapper(): ModelMapper<Word, WordUI>

    fun provideFrequencyUIMapper(): ModelMapper<Frequency, FrequencyUI>

    fun providePartOfSpeechUIMapper(): ModelMapper<PartOfSpeech, PartOfSpeechUI>

    fun provideWordUIResultMapper(): ModelMapper<Result<Word>, UIState<WordUI>>

    fun provideStringPageResultMapper(): ModelMapper<Result<Page<String>>, UIState<Page<String>>>
}
