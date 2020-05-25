package jp.neechan.akari.dictionary.base.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import jp.neechan.akari.dictionary.base.data.framework.tts.TextToSpeechService
import jp.neechan.akari.dictionary.base.domain.entities.Frequency
import jp.neechan.akari.dictionary.base.domain.entities.PartOfSpeech
import jp.neechan.akari.dictionary.base.domain.entities.Word
import jp.neechan.akari.dictionary.base.domain.entities.mappers.ModelMapper
import jp.neechan.akari.dictionary.base.domain.usecases.LoadFilterParamsUseCase
import jp.neechan.akari.dictionary.base.domain.usecases.LoadWordUseCase
import jp.neechan.akari.dictionary.base.presentation.models.FrequencyUI
import jp.neechan.akari.dictionary.base.presentation.models.PartOfSpeechUI
import jp.neechan.akari.dictionary.base.presentation.models.WordUI
import jp.neechan.akari.dictionary.discover.domain.usecases.LoadAllWordsUseCase
import jp.neechan.akari.dictionary.discover.filter.domain.usecases.SaveFilterParamsUseCase
import jp.neechan.akari.dictionary.discover.filter.presentation.WordsFilterViewModel
import jp.neechan.akari.dictionary.discover.presentation.DiscoverViewModel
import jp.neechan.akari.dictionary.home.domain.usecases.DeleteWordUseCase
import jp.neechan.akari.dictionary.home.domain.usecases.LoadSavedWordsUseCase
import jp.neechan.akari.dictionary.home.presentation.HomeViewModel
import jp.neechan.akari.dictionary.home.search.presentation.SearchWordViewModel
import jp.neechan.akari.dictionary.settings.presentation.SettingsViewModel
import jp.neechan.akari.dictionary.word.domain.usecases.SaveWordUseCase
import jp.neechan.akari.dictionary.word.presentation.WordViewModel

class ViewModelFactory(
    private val loadAllWordsUseCase: LoadAllWordsUseCase,
    private val loadSavedWordsUseCase: LoadSavedWordsUseCase,
    private val loadWordUseCase: LoadWordUseCase,
    private val saveWordUseCase: SaveWordUseCase,
    private val deleteWordUseCase: DeleteWordUseCase,
    private val loadFilterParamsUseCase: LoadFilterParamsUseCase,
    private val saveFilterParamsUseCase: SaveFilterParamsUseCase,
    private val ttsService: TextToSpeechService,
    private val wordMapper: ModelMapper<Word, WordUI>,
    private val frequencyMapper: ModelMapper<Frequency, FrequencyUI>,
    private val partOfSpeechMapper: ModelMapper<PartOfSpeech, PartOfSpeechUI>
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when (modelClass) {
            HomeViewModel::class.java -> HomeViewModel(loadSavedWordsUseCase, loadFilterParamsUseCase, deleteWordUseCase)
            SearchWordViewModel::class.java -> SearchWordViewModel(loadWordUseCase, wordMapper)
            WordViewModel::class.java -> WordViewModel(loadWordUseCase, saveWordUseCase, wordMapper, ttsService)
            DiscoverViewModel::class.java -> DiscoverViewModel(loadAllWordsUseCase, loadFilterParamsUseCase)
            WordsFilterViewModel::class.java -> WordsFilterViewModel(
                loadFilterParamsUseCase,
                saveFilterParamsUseCase,
                frequencyMapper,
                partOfSpeechMapper
            )
            SettingsViewModel::class.java -> SettingsViewModel(ttsService)
            else -> throw RuntimeException("Cannot instantiate ViewModel: $modelClass")
        } as T
    }
}