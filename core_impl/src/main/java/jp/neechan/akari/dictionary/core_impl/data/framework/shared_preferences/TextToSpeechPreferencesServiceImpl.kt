package jp.neechan.akari.dictionary.core_impl.data.framework.shared_preferences

import android.content.Context
import androidx.core.content.edit
import dagger.Reusable
import jp.neechan.akari.dictionary.core_impl.data.interface_adapters.TextToSpeechPreferencesService
import java.util.Locale
import javax.inject.Inject

@Reusable
internal class TextToSpeechPreferencesServiceImpl @Inject constructor(context: Context) : TextToSpeechPreferencesService {

    private val preferences = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE)

    companion object {
        private const val PREFERENCES_NAME = "ttsPreferences"
        private const val LOCALE_LANGUAGE = "localeLanguage"
        private const val LOCALE_COUNTRY = "localeCountry"
        private const val VOICE = "voice"
    }

    override suspend fun loadPreferredLocale(): Locale? {
        val localeLanguage = preferences.getString(LOCALE_LANGUAGE, null)
        val localeCountry = preferences.getString(LOCALE_COUNTRY, null)

        return if (localeLanguage != null && localeCountry != null) {
            Locale(localeLanguage, localeCountry)

        } else {
            null
        }
    }

    override suspend fun loadPreferredVoice(): String? {
        return preferences.getString(VOICE, null)
    }

    override suspend fun savePreferredLocale(locale: Locale) {
        preferences.edit {
            putString(LOCALE_LANGUAGE, locale.language)
            putString(LOCALE_COUNTRY, locale.country)
        }
    }

    override suspend fun savePreferredVoice(voice: String) {
        preferences.edit {
            putString(VOICE, voice)
        }
    }
}