package jp.neechan.akari.dictionary.settings.domain.usecases

import dagger.Reusable
import jp.neechan.akari.dictionary.base.domain.usecases.TextToSpeechService
import javax.inject.Inject

@Reusable
class LoadVoicesUseCase @Inject constructor(private val ttsService: TextToSpeechService) {

    suspend operator fun invoke(): List<String> {
        return ttsService.loadLocaleVoices()
    }
}