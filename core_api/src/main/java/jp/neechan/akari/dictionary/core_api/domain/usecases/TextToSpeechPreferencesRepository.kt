package jp.neechan.akari.dictionary.core_api.domain.usecases

import java.util.Locale

interface TextToSpeechPreferencesRepository {

    suspend fun loadPreferredLocale(): Locale?

    suspend fun loadPreferredVoice(): String?

    suspend fun savePreferredLocale(locale: Locale)

    suspend fun savePreferredVoice(voice: String)
}