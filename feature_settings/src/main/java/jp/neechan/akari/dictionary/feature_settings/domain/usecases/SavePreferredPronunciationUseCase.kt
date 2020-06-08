package jp.neechan.akari.dictionary.feature_settings.domain.usecases

import dagger.Reusable
import jp.neechan.akari.dictionary.core_api.domain.usecases.TextToSpeechPreferencesRepository
import jp.neechan.akari.dictionary.core_api.domain.usecases.TextToSpeechService
import java.util.Locale
import javax.inject.Inject

@Reusable
class SavePreferredPronunciationUseCase @Inject constructor(
    private val ttsPreferencesRepository: TextToSpeechPreferencesRepository,
    private val ttsService: TextToSpeechService) {

    suspend operator fun invoke(pronunciation: Locale) {
        ttsPreferencesRepository.savePreferredLocale(pronunciation)
        ttsService.setPreferredLocale(pronunciation)

        // Voice should be changed, too,
        // because the old preferred voice belongs to the old preferred pronunciation.
        val defaultVoice = ttsService.loadDefaultVoice()
        ttsService.setPreferredVoice(defaultVoice)
        ttsPreferencesRepository.savePreferredVoice(defaultVoice)
    }
}