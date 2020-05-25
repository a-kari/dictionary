package jp.neechan.akari.dictionary.base.data.framework.shared_preferences

import android.content.Context
import androidx.core.content.edit
import jp.neechan.akari.dictionary.base.data.framework.dto.FilterParamsDto
import jp.neechan.akari.dictionary.base.data.framework.dto.FrequencyDto
import jp.neechan.akari.dictionary.base.data.framework.dto.PartOfSpeechDto
import jp.neechan.akari.dictionary.base.data.interface_adapters.FilterPreferencesService

class FilterPreferencesServiceImpl(context: Context) : FilterPreferencesService {

    private val preferences = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE)

    companion object {
        private const val PREFERENCES_NAME = "wordsFilterPreferences"
        private const val FREQUENCY = "frequency"
        private const val PART_OF_SPEECH = "partOfSpeech"
    }

    override suspend fun loadFilterParams(): FilterParamsDto {
        val frequency = preferences.getString(FREQUENCY, null)?.let { FrequencyDto.valueOf(it) }
            ?: FilterParamsDto.DEFAULT_FREQUENCY
        val partOfSpeech = preferences.getString(PART_OF_SPEECH, null)?.let { PartOfSpeechDto.valueOf(it) }
            ?: FilterParamsDto.DEFAULT_PART_OF_SPEECH

        return FilterParamsDto(
            FilterParamsDto.DEFAULT_PAGE,
            partOfSpeech,
            frequency
        )
    }

    override suspend fun saveFilterParams(params: FilterParamsDto) {
        preferences.edit {
            putString(FREQUENCY, params.frequency.name)
            putString(PART_OF_SPEECH, params.partOfSpeech.name)
        }
    }
}