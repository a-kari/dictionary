package jp.neechan.akari.dictionary.base.data.interface_adapters

import dagger.Reusable
import jp.neechan.akari.dictionary.base.domain.usecases.TextToSpeechPreferencesRepository
import java.util.Locale
import javax.inject.Inject

@Reusable
class TextToSpeechPreferencesRepositoryImpl @Inject constructor(
    private val ttsPreferencesService: TextToSpeechPreferencesService) : TextToSpeechPreferencesRepository {

    override suspend fun loadPreferredLocale(): Locale? {
        return ttsPreferencesService.loadPreferredLocale()
    }

    override suspend fun loadPreferredVoice(): String? {
        return ttsPreferencesService.loadPreferredVoice()
    }

    override suspend fun savePreferredLocale(locale: Locale) {
        return ttsPreferencesService.savePreferredLocale(locale)
    }

    override suspend fun savePreferredVoice(voice: String) {
        return ttsPreferencesService.savePreferredVoice(voice)
    }
}