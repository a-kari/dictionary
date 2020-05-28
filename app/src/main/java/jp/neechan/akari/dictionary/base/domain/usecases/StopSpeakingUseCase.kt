package jp.neechan.akari.dictionary.base.domain.usecases

class StopSpeakingUseCase(private val ttsService: TextToSpeechService) {

    suspend operator fun invoke() {
        ttsService.stopSpeaking()
    }
}