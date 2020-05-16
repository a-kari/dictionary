package jp.neechan.akari.dictionary.word

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import jp.neechan.akari.dictionary.common.services.TextToSpeechService
import jp.neechan.akari.dictionary.discover.WordsRemoteRepository

class WordViewModel(private val wordsRemoteRepository: WordsRemoteRepository,
                    private val ttsService: TextToSpeechService) : ViewModel() {

    lateinit var wordId: String

    val wordLiveData = liveData {
        emit(wordsRemoteRepository.loadWord(wordId))
    }

    fun speak() {
        ttsService.speak(wordId)
    }

    override fun onCleared() {
        super.onCleared()
        ttsService.stopSpeaking()
    }
}