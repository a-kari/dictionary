package jp.neechan.akari.dictionary.feature_search_word.presentation

import androidx.lifecycle.ViewModel
import jp.neechan.akari.dictionary.base_ui.di.scopes.PerActivity
import jp.neechan.akari.dictionary.base_ui.presentation.viewmodels.BaseViewModelFactory
import jp.neechan.akari.dictionary.core_api.domain.entities.Result
import jp.neechan.akari.dictionary.core_api.domain.entities.Word
import jp.neechan.akari.dictionary.core_api.domain.entities.mappers.ModelMapper
import jp.neechan.akari.dictionary.core_api.presentation.models.UIState
import jp.neechan.akari.dictionary.core_api.presentation.models.WordUI
import jp.neechan.akari.dictionary.domain_words.domain.LoadWordUseCase
import javax.inject.Inject

@PerActivity
class SearchWordViewModelFactory @Inject constructor(
    private val loadWordUseCase: LoadWordUseCase,
    private val wordResultMapper: ModelMapper<Result<Word>, UIState<WordUI>>
) : BaseViewModelFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SearchWordViewModel::class.java)) {
            return SearchWordViewModel(loadWordUseCase, wordResultMapper) as T

        } else {
            throw cannotInstantiateException(modelClass)
        }
    }
}