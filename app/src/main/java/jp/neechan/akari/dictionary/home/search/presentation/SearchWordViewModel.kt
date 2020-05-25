package jp.neechan.akari.dictionary.home.search.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import jp.neechan.akari.dictionary.base.domain.entities.mappers.ModelMapper
import jp.neechan.akari.dictionary.base.domain.entities.Result
import jp.neechan.akari.dictionary.base.domain.entities.Word
import jp.neechan.akari.dictionary.base.domain.usecases.LoadWordUseCase
import jp.neechan.akari.dictionary.base.presentation.models.WordUI

class SearchWordViewModel(private val loadWordUseCase: LoadWordUseCase,
                          private val wordMapper: ModelMapper<Word, WordUI>) : ViewModel() {

    private val queryLiveData = MutableLiveData<String>()

    val resultLiveData = Transformations.switchMap(queryLiveData) { query ->
        liveData {
            if (query.isNotBlank()) {
                emit(Result.Loading)

                val result = loadWordUseCase(query)
                if (result is Result.Success<Word>) {
                    emit(Result.Success(wordMapper.mapToExternalLayer(result.value)))

                // todo: unchecked cast, can be resolved with using UIState instead of Result
                } else {
                    emit(result as Result<WordUI>)
                }
            }
        }
    }

    fun searchWord(query: String) {
        queryLiveData.postValue(query)
    }
}