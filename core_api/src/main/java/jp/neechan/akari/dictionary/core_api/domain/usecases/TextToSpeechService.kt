package jp.neechan.akari.dictionary.core_api.domain.usecases

import java.util.Locale

interface TextToSpeechService {

    suspend fun loadDefaultLocale(): Locale

    suspend fun loadDefaultVoice(): String

    suspend fun loadLocales(): List<Locale>

    suspend fun loadLocaleVoices(): List<String>

    suspend fun setPreferredLocale(locale: Locale)

    suspend fun setPreferredVoice(voiceName: String)

    suspend fun speak(text: String)

    suspend fun stopSpeaking()
}