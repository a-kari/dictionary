package jp.neechan.akari.dictionary.feature_word.presentation

import android.os.Looper
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import jp.neechan.akari.dictionary.core_api.domain.entities.Result
import jp.neechan.akari.dictionary.core_api.domain.entities.Word
import jp.neechan.akari.dictionary.core_api.domain.entities.mappers.ModelMapper
import jp.neechan.akari.dictionary.core_api.presentation.models.FrequencyUI
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
import java.util.Date

internal class WordViewModel(
    private val loadWordUseCase: LoadWordUseCase,
    private val saveWordUseCase: SaveWordUseCase,
    private val resultMapper: ModelMapper<Result<Word>, UIState<WordUI>>,
    private val wordMapper: ModelMapper<Word, WordUI>,
    private val speakUseCase: SpeakUseCase,
    private val stopSpeakingUseCase: StopSpeakingUseCase) : ViewModel() {

    lateinit var wordId: String

    val wordLiveData = liveData {
        // liveData {...} block is started on the UI thread, everything is fine.
        printCurrentThread("LiveData block is started on the UI thread")
        emit(UIState.ShowLoading)

        // Get data on a background thread, it's fine, too.
        // Response is successfully received from MockWebServer.
        val word = withContext(Dispatchers.IO) {
            printCurrentThread("Fetching a word from api on a background thread")
            resultMapper.mapToExternalLayer(loadWordUseCase(wordId))
        }

        // The same result could be with a stubbed value.
//        val word = withContext(Dispatchers.IO) {
//            printCurrentThread("Stubbing a value on a background thread")
//            UIState.ShowContent(WordUI("hello", "həˈləʊ", "hel-lo", FrequencyUI.FREQUENT, null, Date()))
//        }

        // Here is the issue. withContext {...} should switch back to the UI thread
        // after its finish, but it doesn't. emit() is still called on a background thread,
        // which leads to the "Cannot invoke setValue on a background thread" exception.
        //
        // The issue concerns only tests
        // (where I delegate Dispatchers.Main to TestCoroutineDispatcher),
        // i.e. in the main application code switching back to the UI thread goes fine.
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