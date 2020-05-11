package jp.neechan.akari.dictionary.discover.filter

import android.content.Context
import androidx.core.content.edit
import jp.neechan.akari.dictionary.common.models.dto.FrequencyDto
import jp.neechan.akari.dictionary.common.models.dto.PartOfSpeechDto

class WordsFilterPreferencesService(context: Context) {

    private val preferences = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE)

    companion object {
        private const val PREFERENCES_NAME = "wordsFilterPreferences"
        private const val FREQUENCY = "frequency"
        private const val PART_OF_SPEECH = "partOfSpeech"
    }

    fun loadWordsFilterParams(): WordsFilterParams {
        val frequency = preferences.getString(FREQUENCY, null)?.let { FrequencyDto.valueOf(it) }
            ?: WordsFilterParams.DEFAULT_FREQUENCY
        val partOfSpeech = preferences.getString(PART_OF_SPEECH, null)?.let { PartOfSpeechDto.valueOf(it) }
            ?: WordsFilterParams.DEFAULT_PART_OF_SPEECH

        return WordsFilterParams(WordsFilterParams.DEFAULT_PAGE, partOfSpeech, frequency)
    }

    fun saveWordsFilterParams(wordsFilterParams: WordsFilterParams) {
        preferences.edit {
            putString(FREQUENCY, wordsFilterParams.frequency.name)
            putString(PART_OF_SPEECH, wordsFilterParams.partOfSpeech.name)
        }
    }
}