package jp.neechan.akari.dictionary.discover.filter.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import jp.neechan.akari.dictionary.base.domain.entities.mappers.ModelMapper
import jp.neechan.akari.dictionary.base.domain.entities.Frequency
import jp.neechan.akari.dictionary.base.domain.entities.FilterParams
import jp.neechan.akari.dictionary.base.domain.entities.PartOfSpeech
import jp.neechan.akari.dictionary.base.presentation.models.FrequencyUI
import jp.neechan.akari.dictionary.base.presentation.models.PartOfSpeechUI
import jp.neechan.akari.dictionary.base.domain.usecases.LoadFilterParamsUseCase
import jp.neechan.akari.dictionary.discover.filter.domain.usecases.SaveFilterParamsUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class WordsFilterViewModel(private val loadFilterParams: LoadFilterParamsUseCase,
                           private val saveFilterParams: SaveFilterParamsUseCase,
                           private val frequencyMapper: ModelMapper<Frequency, FrequencyUI>,
                           private val partOfSpeechMapper: ModelMapper<PartOfSpeech, PartOfSpeechUI>) : ViewModel() {

    private val frequencies = arrayOf(
        FrequencyUI.VERY_RARE,
        FrequencyUI.RARE,
        FrequencyUI.NORMAL,
        FrequencyUI.FREQUENT,
        FrequencyUI.VERY_FREQUENT
    )
    private val partsOfSpeech = arrayOf(
        PartOfSpeechUI.ALL,
        PartOfSpeechUI.NOUN,
        PartOfSpeechUI.PRONOUN,
        PartOfSpeechUI.ADJECTIVE,
        PartOfSpeechUI.VERB,
        PartOfSpeechUI.ADVERB,
        PartOfSpeechUI.PREPOSITION,
        PartOfSpeechUI.CONJUNCTION
    )

    val frequencyStrings = frequencies.map { it.stringResource }
    val partOfSpeechStrings = partsOfSpeech.map { it.stringResource }

    val preferredFrequencyIndex = liveData {
        val preferredFrequency = loadFilterParams().frequency
        val preferredFrequencyUI = frequencyMapper.mapToExternalLayer(preferredFrequency)
        emit(frequencies.indexOf(preferredFrequencyUI))
    }

    val preferredPartOfSpeechIndex = liveData {
        val preferredPartOfSpeech = loadFilterParams().partOfSpeech
        val preferredPartOfSpeechUI = partOfSpeechMapper.mapToExternalLayer(preferredPartOfSpeech)
        emit(partsOfSpeech.indexOf(preferredPartOfSpeechUI))
    }

    fun updateWordsFilterParams(frequencyIndex: Int, partOfSpeechIndex: Int) = viewModelScope.launch(Dispatchers.IO) {
        val frequency = frequencyMapper.mapToInternalLayer(frequencies[frequencyIndex])
        val partOfSpeech = partOfSpeechMapper.mapToInternalLayer(partsOfSpeech[partOfSpeechIndex])
        val filterParams = FilterParams(frequency = frequency, partOfSpeech = partOfSpeech)
        saveFilterParams(filterParams)
    }
}