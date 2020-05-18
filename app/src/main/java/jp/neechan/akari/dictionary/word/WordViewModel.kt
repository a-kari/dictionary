package jp.neechan.akari.dictionary.word

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import jp.neechan.akari.dictionary.common.models.models.Result
import jp.neechan.akari.dictionary.common.models.models.Word
import jp.neechan.akari.dictionary.common.services.TextToSpeechService
import jp.neechan.akari.dictionary.discover.WordsRemoteRepository
import jp.neechan.akari.dictionary.home.WordsLocalRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class WordViewModel(private val wordsLocalRepository: WordsLocalRepository,
                    private val wordsRemoteRepository: WordsRemoteRepository,
                    private val ttsService: TextToSpeechService) : ViewModel() {

    lateinit var wordId: String

    val wordLiveData = liveData {
        val localWord = wordsLocalRepository.loadWord(wordId)
        if (localWord != null) {
            emit(Result.Success(localWord))

        } else {
            emit(wordsRemoteRepository.loadWord(wordId))
        }
    }

    fun saveWord(word: Word) = viewModelScope.launch(Dispatchers.IO) {
        wordsLocalRepository.saveWord(word)
    }

    fun speak() {
        ttsService.speak(wordId)
    }

    override fun onCleared() {
        super.onCleared()
        ttsService.stopSpeaking()
    }
}