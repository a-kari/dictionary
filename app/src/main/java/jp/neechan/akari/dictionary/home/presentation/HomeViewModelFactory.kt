package jp.neechan.akari.dictionary.home.presentation

import androidx.lifecycle.ViewModel
import jp.neechan.akari.dictionary.base.di.scopes.PerFragment
import jp.neechan.akari.dictionary.base.domain.entities.Page
import jp.neechan.akari.dictionary.base.domain.entities.Result
import jp.neechan.akari.dictionary.base.domain.entities.mappers.ModelMapper
import jp.neechan.akari.dictionary.base.domain.usecases.LoadFilterParamsUseCase
import jp.neechan.akari.dictionary.base.presentation.models.UIState
import jp.neechan.akari.dictionary.base.presentation.viewmodels.BaseViewModelFactory
import jp.neechan.akari.dictionary.home.domain.usecases.DeleteWordUseCase
import jp.neechan.akari.dictionary.home.domain.usecases.LoadSavedWordsUseCase
import javax.inject.Inject

@PerFragment
class HomeViewModelFactory @Inject constructor(
    private val loadWordsUseCase: LoadSavedWordsUseCase,
    private val loadFilterParamsUseCase: LoadFilterParamsUseCase,
    private val deleteWordUseCase: DeleteWordUseCase,
    private val stringPageResultMapper: ModelMapper<Result<Page<String>>, UIState<Page<String>>>) : BaseViewModelFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(
                loadWordsUseCase,
                loadFilterParamsUseCase,
                deleteWordUseCase,
                stringPageResultMapper
            ) as T

        } else {
            throw cannotInstantiateException(modelClass)
        }
    }
}