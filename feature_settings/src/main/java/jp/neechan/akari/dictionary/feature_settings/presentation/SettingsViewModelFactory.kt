package jp.neechan.akari.dictionary.feature_settings.presentation

import androidx.lifecycle.ViewModel
import jp.neechan.akari.dictionary.base_ui.di.scopes.PerFragment
import jp.neechan.akari.dictionary.base_ui.presentation.viewmodels.BaseViewModelFactory
import jp.neechan.akari.dictionary.domain_tts.domain.SpeakUseCase
import jp.neechan.akari.dictionary.domain_tts.domain.StopSpeakingUseCase
import jp.neechan.akari.dictionary.feature_settings.domain.usecases.LoadPreferredPronunciationUseCase
import jp.neechan.akari.dictionary.feature_settings.domain.usecases.LoadPreferredVoiceUseCase
import jp.neechan.akari.dictionary.feature_settings.domain.usecases.LoadPronunciationsUseCase
import jp.neechan.akari.dictionary.feature_settings.domain.usecases.LoadVoicesUseCase
import jp.neechan.akari.dictionary.feature_settings.domain.usecases.SavePreferredPronunciationUseCase
import jp.neechan.akari.dictionary.feature_settings.domain.usecases.SavePreferredVoiceUseCase
import javax.inject.Inject

@PerFragment
internal class SettingsViewModelFactory @Inject constructor(
    private val loadPronunciationsUseCase: LoadPronunciationsUseCase,
    private val loadVoicesUseCase: LoadVoicesUseCase,
    private val loadPreferredPronunciationUseCase: LoadPreferredPronunciationUseCase,
    private val loadPreferredVoiceUseCase: LoadPreferredVoiceUseCase,
    private val savePreferredPronunciationUseCase: SavePreferredPronunciationUseCase,
    private val savePreferredVoiceUseCase: SavePreferredVoiceUseCase,
    private val speakUseCase: SpeakUseCase,
    private val stopSpeakingUseCase: StopSpeakingUseCase
) : BaseViewModelFactory() {

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
