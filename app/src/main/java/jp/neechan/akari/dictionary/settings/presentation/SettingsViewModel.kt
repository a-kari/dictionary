package jp.neechan.akari.dictionary.settings.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.map
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import jp.neechan.akari.dictionary.base.domain.usecases.SpeakUseCase
import jp.neechan.akari.dictionary.base.domain.usecases.StopSpeakingUseCase
import jp.neechan.akari.dictionary.settings.domain.usecases.LoadPreferredPronunciationUseCase
import jp.neechan.akari.dictionary.settings.domain.usecases.LoadPreferredVoiceUseCase
import jp.neechan.akari.dictionary.settings.domain.usecases.LoadPronunciationsUseCase
import jp.neechan.akari.dictionary.settings.domain.usecases.LoadVoicesUseCase
import jp.neechan.akari.dictionary.settings.domain.usecases.SavePreferredPronunciationUseCase
import jp.neechan.akari.dictionary.settings.domain.usecases.SavePreferredVoiceUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.launch
import java.util.Locale

class SettingsViewModel(private val loadPronunciationsUseCase: LoadPronunciationsUseCase,
                        private val loadVoicesUseCase: LoadVoicesUseCase,
                        private val loadPreferredPronunciationUseCase: LoadPreferredPronunciationUseCase,
                        private val loadPreferredVoiceUseCase: LoadPreferredVoiceUseCase,
                        private val savePreferredPronunciationUseCase: SavePreferredPronunciationUseCase,
                        private val savePreferredVoiceUseCase: SavePreferredVoiceUseCase,
                        private val speakUseCase: SpeakUseCase,
                        private val stopSpeakingUseCase: StopSpeakingUseCase) : ViewModel() {

    // pronunciationsLiveData will be populated only once.
    private val pronunciationsLiveData: LiveData<List<Locale>> = liveData {
        emit(loadPronunciationsUseCase())
        initializePreferredPronunciation()
    }
    val pronunciationNamesLiveData = pronunciationsLiveData.map { pronunciations ->
        pronunciations.map { it.displayCountry }
    }

    // _preferredPronunciationIndexLiveData will be populated:
    // 1. After pronunciations are loaded.
    // 2. When a user changes the preferred pronunciation.
    private val _preferredPronunciationIndexLiveData = MutableLiveData<Int>()
    val preferredPronunciationIndexLiveData: LiveData<Int> = _preferredPronunciationIndexLiveData

    // voicesLiveData will be populated:
    // 1. When a user changes the preferred pronunciation, so voices will be changed accordingly.
    val voicesLiveData = _preferredPronunciationIndexLiveData.switchMap { liveData { emit(loadVoicesUseCase()) } }
    private val voicesObserver: Observer<List<String>>

    // _preferredVoiceIndexLiveData will be populated:
    // 1. When voices are changed (due to pronunciation change), so we need to update the preferred voice, too.
    // 2. When a user changes the preferred voice.
    private val _preferredVoiceIndexLiveData = MutableLiveData<Int>()
    val preferredVoiceIndexLiveData: LiveData<Int> = _preferredVoiceIndexLiveData

    init {
        // Trigger the preferred voice update when all voices are updated.
        voicesObserver = Observer { voices ->
            viewModelScope.launch {
                _preferredVoiceIndexLiveData.postValue(voices.indexOf(loadPreferredVoiceUseCase()))
            }
        }
        voicesLiveData.observeForever(voicesObserver)
    }

    private fun initializePreferredPronunciation() = viewModelScope.launch(Dispatchers.IO) {
        val preferredPronunciation = loadPreferredPronunciationUseCase()
        val preferredPronunciationIndex = pronunciationsLiveData.value!!.indexOf(preferredPronunciation)
        _preferredPronunciationIndexLiveData.postValue(preferredPronunciationIndex)
    }

    fun savePreferredPronunciation(preferredPronunciationIndex: Int) = viewModelScope.launch(Dispatchers.IO) {
        val preferredPronunciation = pronunciationsLiveData.value!![preferredPronunciationIndex]
        savePreferredPronunciationUseCase(preferredPronunciation)
        _preferredPronunciationIndexLiveData.postValue(preferredPronunciationIndex)
    }

    fun savePreferredVoice(preferredVoiceIndex: Int) = viewModelScope.launch(Dispatchers.IO) {
        val preferredVoice = voicesLiveData.value!![preferredVoiceIndex]
        savePreferredVoiceUseCase(preferredVoice)
        _preferredVoiceIndexLiveData.postValue(preferredVoiceIndex)
    }

    fun speak(text: String) = viewModelScope.launch {
        speakUseCase(text)
    }

    override fun onCleared() {
        viewModelScope.launch(NonCancellable) { stopSpeakingUseCase() }
        voicesLiveData.removeObserver(voicesObserver)
        super.onCleared()
    }
}