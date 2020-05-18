package jp.neechan.akari.dictionary.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel(private val wordsLocalRepository: WordsLocalRepository) : ViewModel() {

    val wordsLiveData = wordsLocalRepository.wordsLiveData
    var isEditMode = false

    fun deleteWord(word: String) = viewModelScope.launch(Dispatchers.IO) {
        wordsLocalRepository.deleteWord(word)
    }
}