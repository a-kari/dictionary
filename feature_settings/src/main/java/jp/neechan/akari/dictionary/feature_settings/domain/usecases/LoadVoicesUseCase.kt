package jp.neechan.akari.dictionary.feature_settings.domain.usecases

import dagger.Reusable
import jp.neechan.akari.dictionary.core_api.domain.usecases.TextToSpeechService
import javax.inject.Inject

@Reusable
internal class LoadVoicesUseCase @Inject constructor(private val ttsService: TextToSpeechService) {

    suspend operator fun invoke(): List<String> {
        return ttsService.loadLocaleVoices()
    }
}