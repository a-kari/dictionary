package jp.neechan.akari.dictionary.settings

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import jp.neechan.akari.dictionary.R
import jp.neechan.akari.dictionary.common.services.TextToSpeechService
import kotlinx.coroutines.launch
import java.util.*

class SettingsViewModel(private val ttsService: TextToSpeechService) : ViewModel() {

    private val pronunciations = listOf(
        Locale.UK,
        Locale.US,
        Locale("en", "in"),
        Locale("en", "au")
    )
    val pronunciationStrings = listOf(
        R.string.settings_pronunciation_uk,
        R.string.settings_pronunciation_us,
        R.string.settings_pronunciation_india,
        R.string.settings_pronunciation_australia
    )
    var preferredPronunciationIndex = pronunciations.indexOf(ttsService.preferredLocale)
        private set

    private val _voicesLiveData = MutableLiveData<List<String>>()
    val voicesLiveData: LiveData<List<String>> = _voicesLiveData
    var preferredVoiceIndex = -1
        private set

    init {
        refreshVoices()
    }

    private fun refreshVoices() {
        viewModelScope.launch {
            val voices = ttsService.loadVoiceNames()
            _voicesLiveData.postValue(voices)
            preferredVoiceIndex = voices.indexOf(ttsService.preferredVoiceName)
        }
    }

    fun selectPronunciation(pronunciationIndex: Int) {
        preferredPronunciationIndex = pronunciationIndex
        ttsService.selectLocale(pronunciations[pronunciationIndex])
        refreshVoices()
    }

    fun selectVoice(voiceIndex: Int) {
        preferredVoiceIndex = voiceIndex
        ttsService.selectVoice(_voicesLiveData.value!![voiceIndex])
    }

    fun testSpeak() {
        ttsService.speak(R.string.settings_voice_test_phrase)
    }

    override fun onCleared() {
        super.onCleared()
        ttsService.stopSpeaking()
    }
}