package jp.neechan.akari.dictionary.word.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import jp.neechan.akari.dictionary.base.domain.entities.Result
import jp.neechan.akari.dictionary.base.domain.entities.Word
import jp.neechan.akari.dictionary.base.domain.entities.mappers.ModelMapper
import jp.neechan.akari.dictionary.base.domain.usecases.LoadWordUseCase
import jp.neechan.akari.dictionary.base.domain.usecases.SpeakUseCase
import jp.neechan.akari.dictionary.base.domain.usecases.StopSpeakingUseCase
import jp.neechan.akari.dictionary.base.presentation.models.WordUI
import jp.neechan.akari.dictionary.word.domain.usecases.SaveWordUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.launch

class WordViewModel(private val loadWordUseCase: LoadWordUseCase,
                    private val saveWordUseCase: SaveWordUseCase,
                    private val wordMapper: ModelMapper<Word, WordUI>,
                    private val speakUseCase: SpeakUseCase,
                    private val stopSpeakingUseCase: StopSpeakingUseCase) : ViewModel() {

    lateinit var wordId: String

    val wordLiveData = liveData {
        emit(Result.Loading)

        val result = loadWordUseCase(wordId)
        if (result is Result.Success<Word>) {
            emit(Result.Success(wordMapper.mapToExternalLayer(result.value)))

        // todo: unchecked cast, can be resolved with using UIState instead of Result
        } else {
            emit(result as Result<WordUI>)
        }
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