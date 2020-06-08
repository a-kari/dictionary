package jp.neechan.akari.dictionary.feature_settings.domain.usecases

import dagger.Reusable
import jp.neechan.akari.dictionary.core_api.domain.usecases.TextToSpeechPreferencesRepository
import jp.neechan.akari.dictionary.core_api.domain.usecases.TextToSpeechService
import java.util.Locale
import javax.inject.Inject

@Reusable
internal class LoadPreferredPronunciationUseCase @Inject constructor(
    private val ttsPreferencesRepository: TextToSpeechPreferencesRepository,
    private val ttsService: TextToSpeechService) {

    suspend operator fun invoke(): Locale {
        return ttsPreferencesRepository.loadPreferredLocale() ?: ttsService.loadDefaultLocale()
    }
}