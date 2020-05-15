package jp.neechan.akari.dictionary.settings

import android.content.Context
import androidx.core.content.edit
import java.util.*

class TextToSpeechPreferencesService(context: Context) {

    private val preferences = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE)

    companion object {
        private const val PREFERENCES_NAME = "ttsPreferences"
        private const val LOCALE_LANGUAGE = "localeLanguage"
        private const val LOCALE_COUNTRY = "localeCountry"
        private const val VOICE = "voice"
    }

    fun loadPreferredLocale(): Locale? {
        val localeLanguage = preferences.getString(LOCALE_LANGUAGE, null)
        val localeCountry = preferences.getString(LOCALE_COUNTRY, null)

        return if (localeLanguage != null && localeCountry != null) {
            Locale(localeLanguage, localeCountry)

        } else {
            null
        }
    }

    fun loadPreferredVoice(): String? {
        return preferences.getString(VOICE, null)
    }

    fun savePreferredLocale(locale: Locale) {
        preferences.edit {
            putString(LOCALE_LANGUAGE, locale.language)
            putString(LOCALE_COUNTRY, locale.country)
        }
    }

    fun savePreferredVoice(voice: String?) {
        preferences.edit {
            putString(VOICE, voice)
        }
    }
}