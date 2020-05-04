package jp.neechan.akari.dictionary.word

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import jp.neechan.akari.dictionary.discover.WordsRemoteRepository

class WordViewModel(private val wordsRemoteRepository: WordsRemoteRepository) : ViewModel() {

    lateinit var word: String

    val wordLiveData = liveData {
        emit(wordsRemoteRepository.loadWord(word))
    }
}