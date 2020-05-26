package jp.neechan.akari.dictionary.settings.domain.usecases

import jp.neechan.akari.dictionary.base.domain.usecases.TextToSpeechPreferencesRepository
import jp.neechan.akari.dictionary.base.domain.usecases.TextToSpeechService
import java.util.Locale

class SavePreferredPronunciationUseCase(private val ttsPreferencesRepository: TextToSpeechPreferencesRepository,
                                        private val ttsService: TextToSpeechService) {

    suspend operator fun invoke(pronunciation: Locale) {
        ttsPreferencesRepository.savePreferredLocale(pronunciation)
        ttsService.setPreferredLocale(pronunciation)

        // Voice should be changed, too,
        // because the old preferred voice belongs to the old preferred pronunciation.
        ttsPreferencesRepository.savePreferredVoice(ttsService.loadDefaultVoice())
    }
}