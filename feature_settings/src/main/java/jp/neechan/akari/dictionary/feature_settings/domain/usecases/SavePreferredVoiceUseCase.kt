package jp.neechan.akari.dictionary.feature_settings.domain.usecases

import dagger.Reusable
import jp.neechan.akari.dictionary.core_api.domain.usecases.TextToSpeechPreferencesRepository
import jp.neechan.akari.dictionary.core_api.domain.usecases.TextToSpeechService
import javax.inject.Inject

@Reusable
class SavePreferredVoiceUseCase @Inject constructor(
    private val ttsPreferencesRepository: TextToSpeechPreferencesRepository,
    private val ttsService: TextToSpeechService) {

    suspend operator fun invoke(voice: String) {
        ttsPreferencesRepository.savePreferredVoice(voice)
        ttsService.setPreferredVoice(voice)
    }
}