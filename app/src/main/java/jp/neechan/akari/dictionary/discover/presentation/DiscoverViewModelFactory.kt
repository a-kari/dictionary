package jp.neechan.akari.dictionary.discover.presentation

import androidx.lifecycle.ViewModel
import jp.neechan.akari.dictionary.base.di.scopes.PerFragment
import jp.neechan.akari.dictionary.base.domain.entities.Page
import jp.neechan.akari.dictionary.base.domain.entities.Result
import jp.neechan.akari.dictionary.base.domain.entities.mappers.ModelMapper
import jp.neechan.akari.dictionary.base.domain.usecases.LoadFilterParamsUseCase
import jp.neechan.akari.dictionary.base.presentation.models.UIState
import jp.neechan.akari.dictionary.base.presentation.viewmodels.BaseViewModelFactory
import jp.neechan.akari.dictionary.discover.domain.usecases.LoadAllWordsUseCase
import javax.inject.Inject

@PerFragment
class DiscoverViewModelFactory @Inject constructor(
    private val loadWordsUseCase: LoadAllWordsUseCase,
    private val loadFilterParamsUseCase: LoadFilterParamsUseCase,
    private val resultMapper: ModelMapper<Result<Page<String>>, UIState<Page<String>>>) : BaseViewModelFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DiscoverViewModel::class.java)) {
            return DiscoverViewModel(loadWordsUseCase, loadFilterParamsUseCase, resultMapper) as T

        } else {
            throw cannotInstantiateException(modelClass)
        }
    }
}