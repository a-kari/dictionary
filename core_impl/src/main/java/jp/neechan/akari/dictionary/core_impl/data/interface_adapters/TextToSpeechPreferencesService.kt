package jp.neechan.akari.dictionary.core_impl.data.interface_adapters

import java.util.Locale

internal interface TextToSpeechPreferencesService {

    suspend fun loadPreferredLocale(): Locale?

    suspend fun loadPreferredVoice(): String?

    suspend fun savePreferredLocale(locale: Locale)

    suspend fun savePreferredVoice(voice: String)
}
