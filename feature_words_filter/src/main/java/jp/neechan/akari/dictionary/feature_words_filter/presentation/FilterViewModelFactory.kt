package jp.neechan.akari.dictionary.feature_words_filter.presentation

import androidx.lifecycle.ViewModel
import jp.neechan.akari.dictionary.base_ui.di.scopes.PerFragment
import jp.neechan.akari.dictionary.base_ui.presentation.viewmodels.BaseViewModelFactory
import jp.neechan.akari.dictionary.core_api.domain.entities.Frequency
import jp.neechan.akari.dictionary.core_api.domain.entities.PartOfSpeech
import jp.neechan.akari.dictionary.core_api.domain.entities.mappers.ModelMapper
import jp.neechan.akari.dictionary.core_api.presentation.models.FrequencyUI
import jp.neechan.akari.dictionary.core_api.presentation.models.PartOfSpeechUI
import jp.neechan.akari.dictionary.domain_words.domain.LoadFilterParamsUseCase
import jp.neechan.akari.dictionary.feature_words_filter.domain.usecases.SaveFilterParamsUseCase
import javax.inject.Inject

@PerFragment
internal class FilterViewModelFactory @Inject constructor(
    private val loadFilterParams: LoadFilterParamsUseCase,
    private val saveFilterParams: SaveFilterParamsUseCase,
    private val frequencyMapper: ModelMapper<Frequency, FrequencyUI>,
    private val partOfSpeechMapper: ModelMapper<PartOfSpeech, PartOfSpeechUI>
) : BaseViewModelFactory() {

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
