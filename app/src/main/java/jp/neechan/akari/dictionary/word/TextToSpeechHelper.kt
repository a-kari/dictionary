package jp.neechan.akari.dictionary.word

import android.content.Context
import android.speech.tts.TextToSpeech
import android.speech.tts.Voice
import jp.neechan.akari.dictionary.R
import jp.neechan.akari.dictionary.common.toast
import java.util.*

class TextToSpeechHelper(private val context: Context) : TextToSpeech.OnInitListener {

    private val tts = TextToSpeech(context, this)
    private val locale = Locale.US

    companion object {
        private val chooseVoiceCriteria = setOf("en-us", "#female")
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
        val voices = try {
            tts.voices
        } catch (t: Throwable) {
            emptyList<Voice>()
        }

        val preferredVoice = voices.firstOrNull { voice ->
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
            toast(context, R.string.word_tts_cant_play)
        }
    }

    // todo: Create a lint to check if tts was actually shut down.
    fun shutdown() {
        tts.stop()
        tts.shutdown()
    }
}