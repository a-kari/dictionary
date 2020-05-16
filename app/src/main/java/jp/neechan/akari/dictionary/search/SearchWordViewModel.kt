package jp.neechan.akari.dictionary.search

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import jp.neechan.akari.dictionary.common.models.models.Result
import jp.neechan.akari.dictionary.discover.WordsRemoteRepository

class SearchWordViewModel(private val wordsRemoteRepository: WordsRemoteRepository) : ViewModel() {

    private val queryLiveData = MutableLiveData<String>()

    val resultLiveData = Transformations.switchMap(queryLiveData) { query ->
        liveData {
            if (query.isNotBlank()) {
                emit(Result.Loading)
                emit(wordsRemoteRepository.loadWord(query))
            }
        }
    }

    fun searchWord(query: String) {
        queryLiveData.postValue(query)
    }
}