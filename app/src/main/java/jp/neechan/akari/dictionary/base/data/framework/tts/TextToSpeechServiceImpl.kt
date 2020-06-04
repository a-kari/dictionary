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
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TextToSpeechServiceImpl @Inject constructor(
    private val context: Context,
    private val preferencesRepository: TextToSpeechPreferencesRepository
) : TextToSpeechService, TextToSpeech.OnInitListener {

    private val tts = TextToSpeech(context, this)

    @Volatile
    private var ttsState = TextToSpeechState.CREATING

    private lateinit var ttsLocale: Locale

    private val availableLocales: List<Locale>

    private val scope = CoroutineScope(Dispatchers.Default + SupervisorJob())

    /** The service goes through these states in order:
     *
     * 1. CREATING
     * 2. CREATED or ERROR_CREATING
     * 3. LOCALE_CONFIGURED
     * 4. VOICE_CONFIGURED
     *
     * To perform some action, the service must be in (or pass through) the needed state.
     */
    private enum class TextToSpeechState(val stateOrder: Int) {

        /** TTS is being created. */
        CREATING(0),

        /** TTS has been successfully created. */
        CREATED(1),

        /** Error creating TTS. */
        ERROR_CREATING(-1),

        /** TTS' locale is configured. */
        LOCALE_CONFIGURED(2),

        /** TTS' voice is configured. */
        VOICE_CONFIGURED(3)
    }

    init {
        availableLocales = mutableListOf(Locale.UK, Locale.US).apply {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                add(Locale("en", "in"))
                add(Locale("en", "au"))
            }
        }
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            ttsState = TextToSpeechState.CREATED
            scope.launch { configure() }

        } else {
            ttsState = TextToSpeechState.ERROR_CREATING
        }
    }

    private suspend fun configure() {
        val preferredLocale = preferencesRepository.loadPreferredLocale() ?: loadDefaultLocale()
        setPreferredLocale(preferredLocale)
        ttsState = TextToSpeechState.LOCALE_CONFIGURED

        val preferredVoice = preferencesRepository.loadPreferredVoice() ?: loadDefaultVoice()
        setPreferredVoice(preferredVoice)
        ttsState = TextToSpeechState.VOICE_CONFIGURED
    }

    override suspend fun loadDefaultLocale(): Locale {
        return availableLocales.first()
    }

    override suspend fun loadDefaultVoice(): String {
        return doWhenTtsStateIsAtLeast(TextToSpeechState.LOCALE_CONFIGURED) {
            tts.getFineLocaleVoice(ttsLocale)?.name.orEmpty()
        }
    }

    override suspend fun loadLocales(): List<Locale> {
        return availableLocales
    }

    override suspend fun loadLocaleVoices(): List<String> {
        return doWhenTtsStateIsAtLeast(TextToSpeechState.LOCALE_CONFIGURED) {
            tts.getLocaleVoices(ttsLocale).map { it.name }
        }
    }

    override suspend fun setPreferredLocale(locale: Locale) {
        doWhenTtsStateIsAtLeast(TextToSpeechState.CREATED) {
            tts.language = locale
            ttsLocale = locale
        }
    }

    override suspend fun setPreferredVoice(voiceName: String) {
        doWhenTtsStateIsAtLeast(TextToSpeechState.LOCALE_CONFIGURED) {
            val voice = tts.getLocaleVoices(ttsLocale).firstOrNull { it.name == voiceName }
            if (voice != null) {
                tts.voice = voice
            }
        }
    }

    override suspend fun speak(text: String) {
        doWhenTtsStateIsAtLeast(TextToSpeechState.VOICE_CONFIGURED) {
            val result = tts.speak(text, TextToSpeech.QUEUE_FLUSH, null, null)
            if (result == TextToSpeech.ERROR) {
                context.toast(R.string.tts_cant_play)
            }
        }
    }

    override suspend fun stopSpeaking() {
        doWhenTtsStateIsAtLeast(TextToSpeechState.CREATED) {
            tts.stop()
        }
    }

    /** Wait until the service is (at least) in the needed state and perform an action. */
    private suspend inline fun <T> doWhenTtsStateIsAtLeast(neededState: TextToSpeechState, block: () -> T): T {
        while (ttsState.stateOrder < neededState.stateOrder) { delay(1) }
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