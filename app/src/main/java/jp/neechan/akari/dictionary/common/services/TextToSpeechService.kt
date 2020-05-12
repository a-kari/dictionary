package jp.neechan.akari.dictionary.common.services

import android.content.Context
import android.speech.tts.TextToSpeech
import androidx.annotation.StringRes
import jp.neechan.akari.dictionary.R
import jp.neechan.akari.dictionary.common.utils.toast
import kotlinx.coroutines.delay
import java.util.*

class TextToSpeechService(private val context: Context) : TextToSpeech.OnInitListener {

    private val tts = TextToSpeech(context, this)

    @Volatile
    private var isTtsInitialized = false

    var preferredLocale = Locale(DEFAULT_LANGUAGE, DEFAULT_COUNTRY)
        private set

    var preferredVoiceName: String? = null
        private set

    companion object {
        const val DEFAULT_LANGUAGE = "en"
        const val DEFAULT_COUNTRY = "gb"
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            tts.language = preferredLocale
            maybeChangeVoice()
            isTtsInitialized = true
        }
    }

    /** Maybe change TTS voice to: a) a voice preferred by user; b) the most quality one. */
    private fun maybeChangeVoice() {
        // Although voices are available since Lollipop, some Lollipop devices
        // still don't support them: https://issuetracker.google.com/issues/37012397
        val preferredVoice = try {
            if (preferredVoiceName != null) {
                tts.voices.firstOrNull { it.locale == preferredLocale && it.name == preferredVoiceName }

            } else {
                tts.voices.filter { it.locale == preferredLocale }.maxBy { it.quality }
            }

        } catch (t: Throwable) {
            null
        }

        if (preferredVoice != null) {
            preferredVoiceName = preferredVoice.name
            tts.voice = preferredVoice
        }
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

    suspend fun loadVoiceNames(): List<String> {
        return doAfterTtsInitialization {
            try {
                tts.voices.filter { it.locale == preferredLocale }.map { it.name }

            } catch (t: Throwable) {
                emptyList()
            }
        }
    }

    fun selectLocale(locale: Locale) {
        preferredLocale = locale
        tts.language = preferredLocale

        preferredVoiceName = null
        maybeChangeVoice()
    }

    fun selectVoice(voiceName: String) {
        preferredVoiceName = voiceName

        val voice = try {
            tts.voices.firstOrNull { it.name == voiceName }

        } catch (t: Throwable) {
            null
        }

        if (voice != null) {
            tts.voice = voice
        }
    }

    private suspend inline fun <T> doAfterTtsInitialization(block: () -> T): T {
        while (!isTtsInitialized) {
            delay(1)
        }
        return block()
    }
}