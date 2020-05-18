package jp.neechan.akari.dictionary.search

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import jp.neechan.akari.dictionary.common.models.models.Result
import jp.neechan.akari.dictionary.discover.WordsRemoteRepository
import jp.neechan.akari.dictionary.home.WordsLocalRepository

class SearchWordViewModel(private val wordsLocalRepository: WordsLocalRepository,
                          private val wordsRemoteRepository: WordsRemoteRepository) : ViewModel() {

    private val queryLiveData = MutableLiveData<String>()

    val resultLiveData = Transformations.switchMap(queryLiveData) { query ->
        liveData {
            if (query.isNotBlank()) {
                emit(Result.Loading)

                val localWord = wordsLocalRepository.loadWord(query)
                if (localWord != null) {
                    emit(Result.Success(localWord))

                } else {
                    emit(wordsRemoteRepository.loadWord(query))
                }
            }
        }
    }

    fun searchWord(query: String) {
        queryLiveData.postValue(query)
    }
}