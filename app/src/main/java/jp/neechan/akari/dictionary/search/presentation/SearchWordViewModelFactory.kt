package jp.neechan.akari.dictionary.search.presentation

import androidx.lifecycle.ViewModel
import jp.neechan.akari.dictionary.base.di.scopes.PerActivity
import jp.neechan.akari.dictionary.base.domain.entities.Result
import jp.neechan.akari.dictionary.base.domain.entities.Word
import jp.neechan.akari.dictionary.base.domain.entities.mappers.ModelMapper
import jp.neechan.akari.dictionary.base.domain.usecases.LoadWordUseCase
import jp.neechan.akari.dictionary.base.presentation.models.UIState
import jp.neechan.akari.dictionary.base.presentation.models.WordUI
import jp.neechan.akari.dictionary.base.presentation.viewmodels.BaseViewModelFactory
import javax.inject.Inject

@PerActivity
class SearchWordViewModelFactory @Inject constructor(
    private val loadWordUseCase: LoadWordUseCase,
    private val wordResultMapper: ModelMapper<Result<Word>, UIState<WordUI>>) : BaseViewModelFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SearchWordViewModel::class.java)) {
            return SearchWordViewModel(loadWordUseCase, wordResultMapper) as T

        } else {
            throw cannotInstantiateException(modelClass)
        }
    }
}