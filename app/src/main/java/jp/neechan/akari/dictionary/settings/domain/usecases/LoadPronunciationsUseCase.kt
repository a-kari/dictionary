package jp.neechan.akari.dictionary.settings.domain.usecases

import jp.neechan.akari.dictionary.base.domain.usecases.TextToSpeechService
import java.util.Locale

class LoadPronunciationsUseCase(private val ttsService: TextToSpeechService) {

    suspend operator fun invoke(): List<Locale> {
        return ttsService.loadLocales()
    }
}