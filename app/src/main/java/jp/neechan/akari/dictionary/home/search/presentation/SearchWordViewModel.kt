package jp.neechan.akari.dictionary.home.search.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import jp.neechan.akari.dictionary.base.domain.entities.Result
import jp.neechan.akari.dictionary.base.domain.entities.Word
import jp.neechan.akari.dictionary.base.domain.entities.mappers.ModelMapper
import jp.neechan.akari.dictionary.base.domain.usecases.LoadWordUseCase
import jp.neechan.akari.dictionary.base.presentation.models.UIState
import jp.neechan.akari.dictionary.base.presentation.models.WordUI

class SearchWordViewModel(private val loadWordUseCase: LoadWordUseCase,
                          private val resultMapper: ModelMapper<Result<Word>, UIState<WordUI>>) : ViewModel() {

    private val queryLiveData = MutableLiveData<String>()

    val wordLiveData = Transformations.switchMap(queryLiveData) { query ->
        liveData {
            if (query.isNotBlank()) {
                emit(UIState.ShowLoading)
                emit(resultMapper.mapToExternalLayer(loadWordUseCase(query)))
            }
        }
    }

    fun searchWord(query: String) {
        queryLiveData.postValue(query)
    }
}