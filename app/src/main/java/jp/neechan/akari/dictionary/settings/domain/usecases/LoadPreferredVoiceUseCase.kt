package jp.neechan.akari.dictionary.settings.domain.usecases

import dagger.Reusable
import jp.neechan.akari.dictionary.base.domain.usecases.TextToSpeechPreferencesRepository
import jp.neechan.akari.dictionary.base.domain.usecases.TextToSpeechService
import javax.inject.Inject

@Reusable
class LoadPreferredVoiceUseCase @Inject constructor(
    private val ttsPreferencesRepository: TextToSpeechPreferencesRepository,
    private val ttsService: TextToSpeechService) {

    suspend operator fun invoke(): String {
        return ttsPreferencesRepository.loadPreferredVoice() ?: ttsService.loadDefaultVoice()
    }
}