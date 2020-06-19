package jp.neechan.akari.dictionary.base.di

import jp.neechan.akari.dictionary.base.domain.entities.Frequency
import jp.neechan.akari.dictionary.base.domain.entities.Page
import jp.neechan.akari.dictionary.base.domain.entities.PartOfSpeech
import jp.neechan.akari.dictionary.base.domain.entities.Result
import jp.neechan.akari.dictionary.base.domain.entities.Word
import jp.neechan.akari.dictionary.base.domain.entities.mappers.ModelMapper
import jp.neechan.akari.dictionary.base.presentation.models.FrequencyUI
import jp.neechan.akari.dictionary.base.presentation.models.PartOfSpeechUI
import jp.neechan.akari.dictionary.base.presentation.models.UIState
import jp.neechan.akari.dictionary.base.presentation.models.WordUI

interface UIModelMappersProvider {

    fun provideWordUIMapper(): ModelMapper<Word, WordUI>

    fun provideFrequencyUIMapper(): ModelMapper<Frequency, FrequencyUI>

    fun providePartOfSpeechUIMapper(): ModelMapper<PartOfSpeech, PartOfSpeechUI>

    fun provideWordUIResultMapper(): ModelMapper<Result<Word>, UIState<WordUI>>

    fun provideStringPageResultMapper(): ModelMapper<Result<Page<String>>, UIState<Page<String>>>
}