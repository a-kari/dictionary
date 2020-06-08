package jp.neechan.akari.dictionary.feature_word.presentation

import androidx.lifecycle.ViewModel
import jp.neechan.akari.dictionary.base_ui.di.scopes.PerFragment
import jp.neechan.akari.dictionary.base_ui.presentation.viewmodels.BaseViewModelFactory
import jp.neechan.akari.dictionary.core_api.domain.entities.Result
import jp.neechan.akari.dictionary.core_api.domain.entities.Word
import jp.neechan.akari.dictionary.core_api.domain.entities.mappers.ModelMapper
import jp.neechan.akari.dictionary.core_api.presentation.models.UIState
import jp.neechan.akari.dictionary.core_api.presentation.models.WordUI
import jp.neechan.akari.dictionary.domain_tts.domain.SpeakUseCase
import jp.neechan.akari.dictionary.domain_tts.domain.StopSpeakingUseCase
import jp.neechan.akari.dictionary.domain_words.domain.LoadWordUseCase
import jp.neechan.akari.dictionary.feature_word.domain.usecases.SaveWordUseCase
import javax.inject.Inject

@PerFragment
class WordViewModelFactory @Inject constructor(
    private val loadWordUseCase: LoadWordUseCase,
    private val saveWordUseCase: SaveWordUseCase,
    private val resultMapper: ModelMapper<Result<Word>, UIState<WordUI>>,
    private val wordMapper: ModelMapper<Word, WordUI>,
    private val speakUseCase: SpeakUseCase,
    private val stopSpeakingUseCase: StopSpeakingUseCase) : BaseViewModelFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WordViewModel::class.java)) {
            return WordViewModel(
                loadWordUseCase,
                saveWordUseCase,
                resultMapper,
                wordMapper,
                speakUseCase,
                stopSpeakingUseCase
            ) as T

        } else {
            throw cannotInstantiateException(modelClass)
        }
    }
}