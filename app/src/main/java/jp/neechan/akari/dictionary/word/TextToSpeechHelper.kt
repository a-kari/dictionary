package jp.neechan.akari.dictionary.word

import android.content.Context
import android.speech.tts.TextToSpeech
import android.speech.tts.Voice
import jp.neechan.akari.dictionary.R
import jp.neechan.akari.dictionary.common.utils.toast
import java.util.*

class TextToSpeechHelper(private val context: Context) : TextToSpeech.OnInitListener {

    private val tts = TextToSpeech(context, this)
    private val locale = Locale.US
    private lateinit var localeVoices: List<Voice>

    companion object {
        private val chooseVoiceCriteria = setOf("#female")
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            tts.language = locale
            maybeChangeVoice()
        }
    }

    private fun maybeChangeVoice() {
        // Although voices are available since Lollipop, some Lollipop devices
        // still don't support them: https://issuetracker.google.com/issues/37012397
        localeVoices = try {
            tts.voices.toList().filter { it.locale == locale }
        } catch (t: Throwable) {
            emptyList()
        }

        val preferredVoice = localeVoices.firstOrNull { voice ->
            chooseVoiceCriteria.all { criteria ->
                voice.name.contains(criteria, true)
            }
        }

        if (preferredVoice != null) {
            tts.voice = preferredVoice
        }
    }

    fun speak(text: String) {
        val result = tts.speak(text, TextToSpeech.QUEUE_FLUSH, null, null)
        if (result == TextToSpeech.ERROR) {
            toast(
                context,
                R.string.tts_cant_play
            )
        }
    }

    fun testSpeak() {
        speak(context.getString(R.string.tts_service_test_phrase))
    }

    // todo: Create a lint to check if tts was actually shut down.
    fun shutdown() {
        tts.stop()
        tts.shutdown()
    }

    fun loadSelectedLocale(): Locale {
        return locale
    }

    // todo: Suspend until TTS is initialized.
    fun loadVoiceNames(): List<String> {
        return localeVoices.map { it.name }
    }

    fun loadSelectedVoiceName(): String? {
        return tts.voice.name
    }

    fun selectVoice(name: String) {
        val voice = try {
            tts.voices.firstOrNull { it.name == name }

        } catch (t: Throwable) {
            null
        }

        if (voice != null) {
            tts.voice = voice
        }
    }
}