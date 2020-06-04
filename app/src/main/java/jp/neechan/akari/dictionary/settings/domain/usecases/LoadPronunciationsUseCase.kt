package jp.neechan.akari.dictionary.settings.domain.usecases

import dagger.Reusable
import jp.neechan.akari.dictionary.base.domain.usecases.TextToSpeechService
import java.util.Locale
import javax.inject.Inject

@Reusable
class LoadPronunciationsUseCase @Inject constructor(private val ttsService: TextToSpeechService) {

    suspend operator fun invoke(): List<Locale> {
        return ttsService.loadLocales()
    }
}