package jp.neechan.akari.dictionary.common.services

import android.content.Context
import android.os.Build
import android.speech.tts.TextToSpeech
import android.speech.tts.Voice
import androidx.annotation.StringRes
import jp.neechan.akari.dictionary.R
import jp.neechan.akari.dictionary.common.utils.toast
import jp.neechan.akari.dictionary.settings.TextToSpeechPreferencesService
import kotlinx.coroutines.delay
import java.util.*

class TextToSpeechService(private val context: Context,
                          private val preferencesService: TextToSpeechPreferencesService) : TextToSpeech.OnInitListener {

    private val tts = TextToSpeech(context, this)

    @Volatile
    private var isTtsInitialized = false

    val availableLocales: List<Locale>

    var preferredLocale: Locale
        private set

    var preferredVoice: Voice? = null
        private set

    init {
        availableLocales = mutableListOf(Locale.UK, Locale.US).apply {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                add(Locale("en", "in"))
                add(Locale("en", "au"))
            }
        }
        preferredLocale = preferencesService.loadPreferredLocale() ?: availableLocales.first()
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            tts.language = preferredLocale
            initPreferredVoice()
            maybeChangeTtsVoice()
            isTtsInitialized = true
        }
    }

    private fun initPreferredVoice() {
        val preferredVoiceName = preferencesService.loadPreferredVoice()
        preferredVoice = if (preferredVoiceName != null) {
            tts.getLocaleVoices(preferredLocale).firstOrNull { it.name == preferredVoiceName }

        } else {
            tts.getFineVoiceForLocale(preferredLocale)
        }
    }

    private fun maybeChangeTtsVoice() {
        if (preferredVoice != null) {
            tts.voice = preferredVoice
        }
    }

    suspend fun loadLocaleVoices(): List<Voice> {
        return doAfterTtsInitialization { tts.getLocaleVoices(preferredLocale) }
    }

    fun setPreferredLocale(preferredLocale: Locale) {
        this.preferredLocale = preferredLocale
        tts.language = preferredLocale
        preferencesService.savePreferredLocale(preferredLocale)

        setPreferredVoice(tts.getFineVoiceForLocale(preferredLocale))
    }

    fun setPreferredVoice(preferredVoice: Voice?) {
        this.preferredVoice = preferredVoice
        preferencesService.savePreferredVoice(preferredVoice?.name)
        maybeChangeTtsVoice()
    }

    fun speak(text: String) {
        val result = tts.speak(text, TextToSpeech.QUEUE_FLUSH, null, null)
        if (result == TextToSpeech.ERROR) {
            toast(context, R.string.tts_cant_play)
        }
    }

    fun speak(@StringRes textResource: Int) {
        speak(context.getString(textResource))
    }

    fun stopSpeaking() {
        tts.stop()
    }

    private suspend inline fun <T> doAfterTtsInitialization(block: () -> T): T {
        while (!isTtsInitialized) {
            delay(1)
        }
        return block()
    }

    private fun TextToSpeech.getLocaleVoices(locale: Locale): List<Voice> {
        // Although voices are available since Lollipop, some Lollipop devices
        // still don't support them: https://issuetracker.google.com/issues/37012397
        return try {
            voices.filter { it.locale == locale }

        } catch (t: Throwable) {
            emptyList()
        }
    }

    private fun TextToSpeech.getFineVoiceForLocale(locale: Locale): Voice? {
        return getLocaleVoices(locale).maxBy { it.quality }
    }
}