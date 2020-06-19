package jp.neechan.akari.dictionary.feature_home.presentation

import androidx.lifecycle.ViewModel
import jp.neechan.akari.dictionary.base_ui.di.scopes.PerFragment
import jp.neechan.akari.dictionary.base_ui.presentation.viewmodels.BaseViewModelFactory
import jp.neechan.akari.dictionary.core_api.domain.entities.Page
import jp.neechan.akari.dictionary.core_api.domain.entities.Result
import jp.neechan.akari.dictionary.core_api.domain.entities.mappers.ModelMapper
import jp.neechan.akari.dictionary.core_api.presentation.models.UIState
import jp.neechan.akari.dictionary.domain_words.domain.LoadFilterParamsUseCase
import jp.neechan.akari.dictionary.feature_home.domain.usecases.DeleteWordUseCase
import jp.neechan.akari.dictionary.feature_home.domain.usecases.LoadSavedWordsUseCase
import javax.inject.Inject

@PerFragment
internal class HomeViewModelFactory @Inject constructor(
    private val loadWordsUseCase: LoadSavedWordsUseCase,
    private val loadFilterParamsUseCase: LoadFilterParamsUseCase,
    private val deleteWordUseCase: DeleteWordUseCase,
    private val stringPageResultMapper: ModelMapper<Result<Page<String>>, UIState<Page<String>>>
) : BaseViewModelFactory() {

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