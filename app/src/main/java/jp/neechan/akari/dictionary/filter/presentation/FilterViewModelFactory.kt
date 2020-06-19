package jp.neechan.akari.dictionary.filter.presentation

import androidx.lifecycle.ViewModel
import jp.neechan.akari.dictionary.base.di.scopes.PerFragment
import jp.neechan.akari.dictionary.base.domain.entities.Frequency
import jp.neechan.akari.dictionary.base.domain.entities.PartOfSpeech
import jp.neechan.akari.dictionary.base.domain.entities.mappers.ModelMapper
import jp.neechan.akari.dictionary.base.domain.usecases.LoadFilterParamsUseCase
import jp.neechan.akari.dictionary.base.presentation.models.FrequencyUI
import jp.neechan.akari.dictionary.base.presentation.models.PartOfSpeechUI
import jp.neechan.akari.dictionary.base.presentation.viewmodels.BaseViewModelFactory
import jp.neechan.akari.dictionary.filter.domain.usecases.SaveFilterParamsUseCase
import javax.inject.Inject

@PerFragment
class FilterViewModelFactory @Inject constructor(
    private val loadFilterParams: LoadFilterParamsUseCase,
    private val saveFilterParams: SaveFilterParamsUseCase,
    private val frequencyMapper: ModelMapper<Frequency, FrequencyUI>,
    private val partOfSpeechMapper: ModelMapper<PartOfSpeech, PartOfSpeechUI>) : BaseViewModelFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WordsFilterViewModel::class.java)) {
            return WordsFilterViewModel(
                loadFilterParams,
                saveFilterParams,
                frequencyMapper,
                partOfSpeechMapper
            ) as T

        } else {
            throw cannotInstantiateException(modelClass)
        }
    }
}