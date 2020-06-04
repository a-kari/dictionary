package jp.neechan.akari.dictionary.base.domain.usecases

import dagger.Reusable
import javax.inject.Inject

@Reusable
class SpeakUseCase @Inject constructor(private val ttsService: TextToSpeechService) {

    suspend operator fun invoke(text: String) {
        ttsService.speak(text)
    }
}