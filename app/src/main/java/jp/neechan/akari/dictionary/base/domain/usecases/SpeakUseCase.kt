package jp.neechan.akari.dictionary.base.domain.usecases

class SpeakUseCase(private val ttsService: TextToSpeechService) {

    suspend operator fun invoke(text: String) {
        ttsService.speak(text)
    }
}