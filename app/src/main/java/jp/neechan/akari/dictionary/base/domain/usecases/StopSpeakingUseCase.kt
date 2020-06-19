package jp.neechan.akari.dictionary.base.domain.usecases

import dagger.Reusable
import javax.inject.Inject

@Reusable
class StopSpeakingUseCase @Inject constructor(private val ttsService: TextToSpeechService) {

    suspend operator fun invoke() {
        ttsService.stopSpeaking()
    }
}