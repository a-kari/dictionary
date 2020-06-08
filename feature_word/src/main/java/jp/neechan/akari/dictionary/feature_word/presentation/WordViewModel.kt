package jp.neechan.akari.dictionary.feature_word.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import jp.neechan.akari.dictionary.core_api.domain.entities.Result
import jp.neechan.akari.dictionary.core_api.domain.entities.Word
import jp.neechan.akari.dictionary.core_api.domain.entities.mappers.ModelMapper
import jp.neechan.akari.dictionary.core_api.presentation.models.UIState
import jp.neechan.akari.dictionary.core_api.presentation.models.WordUI
import jp.neechan.akari.dictionary.domain_tts.domain.SpeakUseCase
import jp.neechan.akari.dictionary.domain_tts.domain.StopSpeakingUseCase
import jp.neechan.akari.dictionary.domain_words.domain.LoadWordUseCase
import jp.neechan.akari.dictionary.feature_word.domain.usecases.SaveWordUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.launch

class WordViewModel(private val loadWordUseCase: LoadWordUseCase,
                    private val saveWordUseCase: SaveWordUseCase,
                    private val resultMapper: ModelMapper<Result<Word>, UIState<WordUI>>,
                    private val wordMapper: ModelMapper<Word, WordUI>,
                    private val speakUseCase: SpeakUseCase,
                    private val stopSpeakingUseCase: StopSpeakingUseCase) : ViewModel() {

    lateinit var wordId: String

    val wordLiveData = liveData {
        emit(UIState.ShowLoading)
        emit(resultMapper.mapToExternalLayer(loadWordUseCase(wordId)))
    }

    fun saveWord(wordUI: WordUI) = viewModelScope.launch(Dispatchers.IO) {
        val word = wordMapper.mapToInternalLayer(wordUI)
        saveWordUseCase(word)
    }

    fun speak() = viewModelScope.launch {
        speakUseCase(wordId)
    }

    override fun onCleared() {
        viewModelScope.launch(NonCancellable) { stopSpeakingUseCase() }
        super.onCleared()
    }
}