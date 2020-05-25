package jp.neechan.akari.dictionary.settings.presentation

import android.speech.tts.Voice
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import jp.neechan.akari.dictionary.R
import jp.neechan.akari.dictionary.base.data.framework.tts.TextToSpeechService
import kotlinx.coroutines.launch

class SettingsViewModel(private val ttsService: TextToSpeechService) : ViewModel() {

    private val pronunciations = ttsService.availableLocales
    val pronunciationNames = pronunciations.map { it.displayCountry }
    var preferredPronunciationIndex = pronunciations.indexOf(ttsService.preferredLocale)
        private set

    private val voicesLiveData = MutableLiveData<List<Voice>>()
    val voiceNamesLiveData = Transformations.map(voicesLiveData) { voices -> voices.map { it.name } }
    var preferredVoiceIndex = -1
        private set

    init {
        refreshVoices()
    }

    private fun refreshVoices() {
        viewModelScope.launch {
            val voices = ttsService.loadLocaleVoices()
            voicesLiveData.postValue(voices)
            preferredVoiceIndex = voices.indexOf(ttsService.preferredVoice)
        }
    }

    fun selectPronunciation(pronunciationIndex: Int) {
        preferredPronunciationIndex = pronunciationIndex
        ttsService.setPreferredLocale(pronunciations[pronunciationIndex])
        refreshVoices()
    }

    fun selectVoice(voiceIndex: Int) {
        preferredVoiceIndex = voiceIndex
        ttsService.setPreferredVoice(voicesLiveData.value!![voiceIndex])
    }

    fun testSpeak() {
        ttsService.speak(R.string.settings_voice_test_phrase)
    }

    override fun onCleared() {
        super.onCleared()
        ttsService.stopSpeaking()
    }
}