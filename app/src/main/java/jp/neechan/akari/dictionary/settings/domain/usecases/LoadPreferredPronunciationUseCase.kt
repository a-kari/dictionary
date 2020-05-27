package jp.neechan.akari.dictionary.settings.domain.usecases

import jp.neechan.akari.dictionary.base.domain.usecases.TextToSpeechPreferencesRepository
import jp.neechan.akari.dictionary.base.domain.usecases.TextToSpeechService
import java.util.Locale

class LoadPreferredPronunciationUseCase(private val ttsPreferencesRepository: TextToSpeechPreferencesRepository,
                                        private val ttsService: TextToSpeechService) {

    suspend operator fun invoke(): Locale {
        return ttsPreferencesRepository.loadPreferredLocale() ?: ttsService.loadDefaultLocale()
    }
}