package jp.neechan.akari.dictionary.feature_word.presentation

import android.os.Looper
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
import kotlinx.coroutines.withContext

internal class WordViewModel(
    private val loadWordUseCase: LoadWordUseCase,
    private val saveWordUseCase: SaveWordUseCase,
    private val resultMapper: ModelMapper<Result<Word>, UIState<WordUI>>,
    private val wordMapper: ModelMapper<Word, WordUI>,
    private val speakUseCase: SpeakUseCase,
    private val stopSpeakingUseCase: StopSpeakingUseCase) : ViewModel() {

    lateinit var wordId: String

    val wordLiveData = liveData {
        // Блок liveData {...} запустился в UI-потоке, всё нормально.
        // Эмичу UIState.ShowLoading, чтобы показывался ProgressBar.
        printCurrentThread("LiveData block is started on the UI thread")
        emit(UIState.ShowLoading)

        // Получаю данные в background-потоке, здесь тоже всё нормально.
        // Ответ успешно приходит с MockWebServer'a.
        val word = withContext(Dispatchers.IO) {
            printCurrentThread("Fetching a word from api on a background thread")
            resultMapper.mapToExternalLayer(loadWordUseCase(wordId))
        }

        // Вот здесь проблема. Функция withContext {...} после своего завершения должна переключиться
        // обратно на UI-поток, но она не переключается. Функция emit() вызывается в
        // background-потоке, что ведет к ошибке "Cannot invoke setValue on a background thread".
        //
        // Эта проблема касается только тестов, где я подменяю Dispatchers.Main на TestCoroutineDispatcher,
        // т.е. в основном коде приложения переключение на UI-поток происходит нормально.
        printCurrentThread("withContext {...} has ended and should switch back to Dispatchers.Main, but it doesn't")
        emit(word)
    }

    private fun printCurrentThread(message: String) {
        val threadInfo = "Current thread id: ${Thread.currentThread().id}. UI thread id: ${Looper.getMainLooper().thread.id}"
        println("=================================================================================")
        println(message)
        println(threadInfo)
        println("=================================================================================")
        println()
        println()
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