package jp.neechan.akari.dictionary.domain_tts.domain

import dagger.Reusable
import jp.neechan.akari.dictionary.core_api.domain.usecases.TextToSpeechService
import javax.inject.Inject

@Reusable
class StopSpeakingUseCase @Inject constructor(private val ttsService: TextToSpeechService) {

    suspend operator fun invoke() {
        ttsService.stopSpeaking()
    }
}