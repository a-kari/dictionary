package jp.neechan.akari.dictionary.settings.domain.usecases

import jp.neechan.akari.dictionary.base.domain.usecases.TextToSpeechPreferencesRepository
import jp.neechan.akari.dictionary.base.domain.usecases.TextToSpeechService

class LoadPreferredVoiceUseCase(private val ttsPreferencesRepository: TextToSpeechPreferencesRepository,
                                private val ttsService: TextToSpeechService) {

    suspend operator fun invoke(): String {
        return ttsPreferencesRepository.loadPreferredVoice() ?: ttsService.loadDefaultVoice()
    }
}