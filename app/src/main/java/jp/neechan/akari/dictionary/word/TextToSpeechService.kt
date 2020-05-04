package jp.neechan.akari.dictionary.word

import android.content.Context
import android.speech.tts.TextToSpeech
import jp.neechan.akari.dictionary.R
import jp.neechan.akari.dictionary.common.utils.toast
import java.util.*

class TextToSpeechService(private val context: Context) : TextToSpeech.OnInitListener {

    // todo: Load locale & preferred voice from user preferences.
    private val tts = TextToSpeech(context, this)
    private val locale = Locale.US

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            tts.language = locale
        }
    }

    fun speak(text: String) {
        val result = tts.speak(text, TextToSpeech.QUEUE_FLUSH, null, null)
        if (result == TextToSpeech.ERROR) {
            toast(context, R.string.tts_cant_play)
        }
    }

    fun stopSpeaking() {
        tts.stop()
    }
}