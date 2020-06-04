package jp.neechan.akari.dictionary.settings.domain.usecases

import dagger.Reusable
import jp.neechan.akari.dictionary.base.domain.usecases.TextToSpeechPreferencesRepository
import jp.neechan.akari.dictionary.base.domain.usecases.TextToSpeechService
import java.util.Locale
import javax.inject.Inject

@Reusable
class LoadPreferredPronunciationUseCase @Inject constructor(
    private val ttsPreferencesRepository: TextToSpeechPreferencesRepository,
    private val ttsService: TextToSpeechService) {

    suspend operator fun invoke(): Locale {
        return ttsPreferencesRepository.loadPreferredLocale() ?: ttsService.loadDefaultLocale()
    }
}