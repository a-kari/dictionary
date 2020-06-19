package jp.neechan.akari.dictionary.settings.presentation

import androidx.lifecycle.ViewModel
import jp.neechan.akari.dictionary.base.di.scopes.PerFragment
import jp.neechan.akari.dictionary.base.domain.usecases.SpeakUseCase
import jp.neechan.akari.dictionary.base.domain.usecases.StopSpeakingUseCase
import jp.neechan.akari.dictionary.base.presentation.viewmodels.BaseViewModelFactory
import jp.neechan.akari.dictionary.settings.domain.usecases.LoadPreferredPronunciationUseCase
import jp.neechan.akari.dictionary.settings.domain.usecases.LoadPreferredVoiceUseCase
import jp.neechan.akari.dictionary.settings.domain.usecases.LoadPronunciationsUseCase
import jp.neechan.akari.dictionary.settings.domain.usecases.LoadVoicesUseCase
import jp.neechan.akari.dictionary.settings.domain.usecases.SavePreferredPronunciationUseCase
import jp.neechan.akari.dictionary.settings.domain.usecases.SavePreferredVoiceUseCase
import javax.inject.Inject

@PerFragment
class SettingsViewModelFactory @Inject constructor(
    private val loadPronunciationsUseCase: LoadPronunciationsUseCase,
    private val loadVoicesUseCase: LoadVoicesUseCase,
    private val loadPreferredPronunciationUseCase: LoadPreferredPronunciationUseCase,
    private val loadPreferredVoiceUseCase: LoadPreferredVoiceUseCase,
    private val savePreferredPronunciationUseCase: SavePreferredPronunciationUseCase,
    private val savePreferredVoiceUseCase: SavePreferredVoiceUseCase,
    private val speakUseCase: SpeakUseCase,
    private val stopSpeakingUseCase: StopSpeakingUseCase) : BaseViewModelFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SettingsViewModel::class.java)) {
            return SettingsViewModel(
                loadPronunciationsUseCase,
                loadVoicesUseCase,
                loadPreferredPronunciationUseCase,
                loadPreferredVoiceUseCase,
                savePreferredPronunciationUseCase,
                savePreferredVoiceUseCase,
                speakUseCase,
                stopSpeakingUseCase
            ) as T

        } else {
            throw cannotInstantiateException(modelClass)
        }
    }
}