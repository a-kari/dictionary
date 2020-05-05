package jp.neechan.akari.dictionary.discover

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData

class DiscoverViewModel(private val wordsRemoteRepository: WordsRemoteRepository) : ViewModel() {

    val words = liveData {
        emit(wordsRemoteRepository.loadWords())
    }
}