package jp.neechan.akari.dictionary.base.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import jp.neechan.akari.dictionary.base.domain.entities.Frequency
import jp.neechan.akari.dictionary.base.domain.entities.Page
import jp.neechan.akari.dictionary.base.domain.entities.PartOfSpeech
import jp.neechan.akari.dictionary.base.domain.entities.Result
import jp.neechan.akari.dictionary.base.domain.entities.Word
import jp.neechan.akari.dictionary.base.domain.entities.mappers.ModelMapper
import jp.neechan.akari.dictionary.base.domain.usecases.LoadFilterParamsUseCase
import jp.neechan.akari.dictionary.base.domain.usecases.LoadWordUseCase
import jp.neechan.akari.dictionary.base.domain.usecases.SpeakUseCase
import jp.neechan.akari.dictionary.base.domain.usecases.StopSpeakingUseCase
import jp.neechan.akari.dictionary.base.presentation.models.FrequencyUI
import jp.neechan.akari.dictionary.base.presentation.models.PartOfSpeechUI
import jp.neechan.akari.dictionary.base.presentation.models.UIState
import jp.neechan.akari.dictionary.base.presentation.models.WordUI
import jp.neechan.akari.dictionary.discover.domain.usecases.LoadAllWordsUseCase
import jp.neechan.akari.dictionary.discover.filter.domain.usecases.SaveFilterParamsUseCase
import jp.neechan.akari.dictionary.discover.filter.presentation.WordsFilterViewModel
import jp.neechan.akari.dictionary.discover.presentation.DiscoverViewModel
import jp.neechan.akari.dictionary.home.domain.usecases.DeleteWordUseCase
import jp.neechan.akari.dictionary.home.domain.usecases.LoadSavedWordsUseCase
import jp.neechan.akari.dictionary.home.presentation.HomeViewModel
import jp.neechan.akari.dictionary.home.search.presentation.SearchWordViewModel
import jp.neechan.akari.dictionary.settings.domain.usecases.LoadPreferredPronunciationUseCase
import jp.neechan.akari.dictionary.settings.domain.usecases.LoadPreferredVoiceUseCase
import jp.neechan.akari.dictionary.settings.domain.usecases.LoadPronunciationsUseCase
import jp.neechan.akari.dictionary.settings.domain.usecases.LoadVoicesUseCase
import jp.neechan.akari.dictionary.settings.domain.usecases.SavePreferredPronunciationUseCase
import jp.neechan.akari.dictionary.settings.domain.usecases.SavePreferredVoiceUseCase
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
    private val wordMapper: ModelMapper<Word, WordUI>,
    private val frequencyMapper: ModelMapper<Frequency, FrequencyUI>,
    private val partOfSpeechMapper: ModelMapper<PartOfSpeech, PartOfSpeechUI>,
    private val loadPronunciationsUseCase: LoadPronunciationsUseCase,
    private val loadVoicesUseCase: LoadVoicesUseCase,
    private val loadPreferredPronunciationUseCase: LoadPreferredPronunciationUseCase,
    private val loadPreferredVoiceUseCase: LoadPreferredVoiceUseCase,
    private val savePreferredPronunciationUseCase: SavePreferredPronunciationUseCase,
    private val savePreferredVoiceUseCase: SavePreferredVoiceUseCase,
    private val speakUseCase: SpeakUseCase,
    private val stopSpeakingUseCase: StopSpeakingUseCase,
    private val resultWordMapper: ModelMapper<Result<Word>, UIState<WordUI>>,
    private val resultStringPageMapper: ModelMapper<Result<Page<String>>, UIState<Page<String>>>
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when (modelClass) {
            HomeViewModel::class.java -> HomeViewModel(
                loadSavedWordsUseCase,
                loadFilterParamsUseCase,
                deleteWordUseCase,
                resultStringPageMapper
            )
            SearchWordViewModel::class.java -> SearchWordViewModel(
                loadWordUseCase,
                resultWordMapper
            )
            WordViewModel::class.java -> WordViewModel(
                loadWordUseCase,
                saveWordUseCase,
                resultWordMapper,
                wordMapper,
                speakUseCase,
                stopSpeakingUseCase
            )
            DiscoverViewModel::class.java -> DiscoverViewModel(
                loadAllWordsUseCase,
                loadFilterParamsUseCase,
                resultStringPageMapper
            )
            WordsFilterViewModel::class.java -> WordsFilterViewModel(
                loadFilterParamsUseCase,
                saveFilterParamsUseCase,
                frequencyMapper,
                partOfSpeechMapper
            )
            SettingsViewModel::class.java -> SettingsViewModel(
                loadPronunciationsUseCase,
                loadVoicesUseCase,
                loadPreferredPronunciationUseCase,
                loadPreferredVoiceUseCase,
                savePreferredPronunciationUseCase,
                savePreferredVoiceUseCase,
                speakUseCase,
                stopSpeakingUseCase
            )
            else -> throw RuntimeException("Cannot instantiate ViewModel: $modelClass")
        } as T
    }
}