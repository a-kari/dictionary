package jp.neechan.akari.dictionary.core_impl.presentation.models.mappers

import jp.neechan.akari.dictionary.core_api.domain.entities.Result
import jp.neechan.akari.dictionary.core_api.domain.entities.mappers.ModelMapper
import jp.neechan.akari.dictionary.core_api.presentation.models.UIState

/**
 *  Maps Result<R> into UIState<U>.
 *
 *  Can be used to map e.g. from Result<Word> to UIState<WordUI>
 *  if contentMapper is present as ModelMapper<Word, WordUI>.
 *
 *  If contentMapper is not present, then the mapper assumes that content classes are the same
 *  and just converts Result<R> into UIState<R> and vise versa.
 *
 *  @param R is a core model class
 *  @param U is a UI model class
 *  @property contentMapper maps from a core model into a UI model
 */
internal class ResultToUIStateMapper<R, U>(private val contentMapper: ModelMapper<R, U>? = null) :
    ModelMapper<Result<R>, UIState<U>> {

    override fun mapToInternalLayer(externalLayerModel: UIState<U>): Result<R> {
        return when (externalLayerModel) {
            is UIState.ShowContent -> {
                val value = contentMapper?.mapToInternalLayer(externalLayerModel.content)
                            ?: externalLayerModel.content as R
                Result.Success(value)
            }
            UIState.ShowLoading -> Result.Loading
            UIState.ShowConnectionError -> Result.ConnectionError
            UIState.ShowNotFoundError -> Result.NotFoundError
            is UIState.ShowError -> Result.Error(externalLayerModel.error)
        }
    }

    override fun mapToExternalLayer(internalLayerModel: Result<R>): UIState<U> {
        return when (internalLayerModel) {
            is Result.Success -> {
                val content = contentMapper?.mapToExternalLayer(internalLayerModel.value)
                              ?: internalLayerModel.value as U
                UIState.ShowContent(content)
            }
            Result.Loading -> UIState.ShowLoading
            Result.ConnectionError -> UIState.ShowConnectionError
            Result.NotFoundError -> UIState.ShowNotFoundError
            is Result.Error -> UIState.ShowError(internalLayerModel.error)
        }
    }
}