package jp.neechan.akari.dictionary.base.data.framework.tts

import android.content.Context
import android.os.Build
import android.speech.tts.TextToSpeech
import android.speech.tts.Voice
import jp.neechan.akari.dictionary.R
import jp.neechan.akari.dictionary.base.domain.usecases.TextToSpeechPreferencesRepository
import jp.neechan.akari.dictionary.base.domain.usecases.TextToSpeechService
import jp.neechan.akari.dictionary.base.presentation.extensions.toast
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.Locale

class TextToSpeechServiceImpl(
    private val context: Context,
    private val preferencesRepository: TextToSpeechPreferencesRepository
) : TextToSpeechService, TextToSpeech.OnInitListener {

    private val tts = TextToSpeech(context, this)

    @Volatile
    private var isTtsInitialized = false

    private lateinit var ttsLocale: Locale

    private val availableLocales: List<Locale>

    private val scope = CoroutineScope(Dispatchers.Default + SupervisorJob())

    init {
        availableLocales = mutableListOf(Locale.UK, Locale.US).apply {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                add(Locale("en", "in"))
                add(Locale("en", "au"))
            }
        }
    }

    override fun onInit(status: Int) {
        isTtsInitialized = true
        scope.launch { setup() }
    }

    private suspend fun setup() {
        val preferredLocale = preferencesRepository.loadPreferredLocale() ?: loadDefaultLocale()
        setPreferredLocale(preferredLocale)

        val preferredVoice = preferencesRepository.loadPreferredVoice() ?: loadDefaultVoice()
        setPreferredVoice(preferredVoice)
    }

    override suspend fun loadDefaultLocale(): Locale {
        return availableLocales.first()
    }

    override suspend fun loadDefaultVoice(): String {
        return doAfterTtsInitialization {
            tts.getFineLocaleVoice(ttsLocale)?.name.orEmpty()
        }
    }

    override suspend fun loadLocales(): List<Locale> {
        return availableLocales
    }

    override suspend fun loadLocaleVoices(): List<String> {
        return doAfterTtsInitialization {
            tts.getLocaleVoices(ttsLocale).map { it.name }
        }
    }

    override suspend fun setPreferredLocale(locale: Locale) {
        doAfterTtsInitialization {
            tts.language = locale
            ttsLocale = locale
            setPreferredVoice(loadDefaultVoice())
        }
    }

    override suspend fun setPreferredVoice(voiceName: String) {
        doAfterTtsInitialization {
            val voice = tts.getLocaleVoices(ttsLocale).firstOrNull { it.name == voiceName }
            if (voice != null) {
                tts.voice = voice
            }
        }
    }

    override suspend fun speak(text: String) {
        doAfterTtsInitialization {
            val result = tts.speak(text, TextToSpeech.QUEUE_FLUSH, null, null)
            if (result == TextToSpeech.ERROR) {
                context.toast(R.string.tts_cant_play)
            }
        }
    }

    override suspend fun stopSpeaking() {
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

    private fun TextToSpeech.getFineLocaleVoice(locale: Locale): Voice? {
        return getLocaleVoices(locale).maxBy { it.quality }
    }
}