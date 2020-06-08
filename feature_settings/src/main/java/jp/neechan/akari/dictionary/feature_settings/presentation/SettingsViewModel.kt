package jp.neechan.akari.dictionary.feature_settings.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.map
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import jp.neechan.akari.dictionary.domain_tts.domain.SpeakUseCase
import jp.neechan.akari.dictionary.domain_tts.domain.StopSpeakingUseCase
import jp.neechan.akari.dictionary.feature_settings.domain.usecases.LoadPreferredPronunciationUseCase
import jp.neechan.akari.dictionary.feature_settings.domain.usecases.LoadPreferredVoiceUseCase
import jp.neechan.akari.dictionary.feature_settings.domain.usecases.LoadPronunciationsUseCase
import jp.neechan.akari.dictionary.feature_settings.domain.usecases.LoadVoicesUseCase
import jp.neechan.akari.dictionary.feature_settings.domain.usecases.SavePreferredPronunciationUseCase
import jp.neechan.akari.dictionary.feature_settings.domain.usecases.SavePreferredVoiceUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.launch
import java.util.Locale

internal class SettingsViewModel(
    private val loadPronunciationsUseCase: LoadPronunciationsUseCase,
    private val loadVoicesUseCase: LoadVoicesUseCase,
    private val loadPreferredPronunciationUseCase: LoadPreferredPronunciationUseCase,
    private val loadPreferredVoiceUseCase: LoadPreferredVoiceUseCase,
    private val savePreferredPronunciationUseCase: SavePreferredPronunciationUseCase,
    private val savePreferredVoiceUseCase: SavePreferredVoiceUseCase,
    private val speakUseCase: SpeakUseCase,
    private val stopSpeakingUseCase: StopSpeakingUseCase) : ViewModel() {

    // pronunciations will be populated only once.
    private val pronunciations: LiveData<List<Locale>> = liveData {
        emit(loadPronunciationsUseCase())
        initializePreferredPronunciation()
    }
    val pronunciationNames = pronunciations.map { pronunciations ->
        pronunciations.map { it.displayCountry }
    }

    // _preferredPronunciationIndex will be populated:
    // 1. After pronunciations are loaded.
    // 2. When a user changes the preferred pronunciation.
    private val _preferredPronunciationIndex = MutableLiveData<Int>()
    val preferredPronunciationIndex: LiveData<Int> = _preferredPronunciationIndex

    // voices will be populated:
    // 1. When a user changes the preferred pronunciation, so voices will be changed accordingly.
    val voices = _preferredPronunciationIndex.switchMap { liveData { emit(loadVoicesUseCase()) } }
    private val voicesObserver: Observer<List<String>>

    // _preferredVoiceIndex will be populated:
    // 1. When voices are changed (due to pronunciation change), so we need to update the preferred voice, too.
    // 2. When a user changes the preferred voice.
    private val _preferredVoiceIndex = MutableLiveData<Int>()
    val preferredVoiceIndex: LiveData<Int> = _preferredVoiceIndex

    init {
        // Trigger the preferred voice update when all voices are updated.
        voicesObserver = Observer { voices ->
            viewModelScope.launch {
                _preferredVoiceIndex.postValue(voices.indexOf(loadPreferredVoiceUseCase()))
            }
        }
        voices.observeForever(voicesObserver)
    }

    private fun initializePreferredPronunciation() = viewModelScope.launch(Dispatchers.IO) {
        val preferredPronunciation = loadPreferredPronunciationUseCase()
        val preferredPronunciationIndex = pronunciations.value!!.indexOf(preferredPronunciation)
        _preferredPronunciationIndex.postValue(preferredPronunciationIndex)
    }

    fun savePreferredPronunciation(preferredPronunciationIndex: Int) = viewModelScope.launch(Dispatchers.IO) {
        val preferredPronunciation = pronunciations.value!![preferredPronunciationIndex]
        savePreferredPronunciationUseCase(preferredPronunciation)
        _preferredPronunciationIndex.postValue(preferredPronunciationIndex)
    }

    fun savePreferredVoice(preferredVoiceIndex: Int) = viewModelScope.launch(Dispatchers.IO) {
        val preferredVoice = voices.value!![preferredVoiceIndex]
        savePreferredVoiceUseCase(preferredVoice)
        _preferredVoiceIndex.postValue(preferredVoiceIndex)
    }

    fun speak(text: String) = viewModelScope.launch {
        speakUseCase(text)
    }

    override fun onCleared() {
        viewModelScope.launch(NonCancellable) { stopSpeakingUseCase() }
        voices.removeObserver(voicesObserver)
        super.onCleared()
    }
}