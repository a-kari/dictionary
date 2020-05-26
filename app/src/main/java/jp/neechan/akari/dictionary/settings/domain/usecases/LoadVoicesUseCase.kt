package jp.neechan.akari.dictionary.settings.domain.usecases

import jp.neechan.akari.dictionary.base.domain.usecases.TextToSpeechService

class LoadVoicesUseCase(private val ttsService: TextToSpeechService) {

    suspend operator fun invoke(): List<String> {
        return ttsService.loadLocaleVoices()
    }
}