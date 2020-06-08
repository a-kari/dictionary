package jp.neechan.akari.dictionary.core_impl.data.framework.shared_preferences

import android.content.Context
import androidx.core.content.edit
import dagger.Reusable
import jp.neechan.akari.dictionary.core_api.domain.entities.FilterParams
import jp.neechan.akari.dictionary.core_api.domain.entities.Frequency
import jp.neechan.akari.dictionary.core_api.domain.entities.PartOfSpeech
import jp.neechan.akari.dictionary.core_impl.data.interface_adapters.FilterPreferencesService
import javax.inject.Inject

@Reusable
class FilterPreferencesServiceImpl @Inject constructor(context: Context) : FilterPreferencesService {

    private val preferences = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE)

    companion object {
        private const val PREFERENCES_NAME = "wordsFilterPreferences"
        private const val FREQUENCY = "frequency"
        private const val PART_OF_SPEECH = "partOfSpeech"
    }

    override suspend fun loadFilterParams(): FilterParams {
        val frequency = preferences.getString(FREQUENCY, null)?.let { Frequency.valueOf(it) }
            ?: FilterParams.DEFAULT_FREQUENCY
        val partOfSpeech = preferences.getString(PART_OF_SPEECH, null)?.let { PartOfSpeech.valueOf(it) }
            ?: FilterParams.DEFAULT_PART_OF_SPEECH

        return FilterParams(
            FilterParams.DEFAULT_PAGE,
            partOfSpeech,
            frequency
        )
    }

    override suspend fun saveFilterParams(params: FilterParams) {
        preferences.edit {
            putString(FREQUENCY, params.frequency.name)
            putString(PART_OF_SPEECH, params.partOfSpeech.name)
        }
    }
}