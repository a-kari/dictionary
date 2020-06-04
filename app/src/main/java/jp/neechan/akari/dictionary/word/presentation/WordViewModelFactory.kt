package jp.neechan.akari.dictionary.word.presentation

import androidx.lifecycle.ViewModel
import jp.neechan.akari.dictionary.base.di.scopes.PerFragment
import jp.neechan.akari.dictionary.base.domain.entities.Result
import jp.neechan.akari.dictionary.base.domain.entities.Word
import jp.neechan.akari.dictionary.base.domain.entities.mappers.ModelMapper
import jp.neechan.akari.dictionary.base.domain.usecases.LoadWordUseCase
import jp.neechan.akari.dictionary.base.domain.usecases.SpeakUseCase
import jp.neechan.akari.dictionary.base.domain.usecases.StopSpeakingUseCase
import jp.neechan.akari.dictionary.base.presentation.models.UIState
import jp.neechan.akari.dictionary.base.presentation.models.WordUI
import jp.neechan.akari.dictionary.base.presentation.viewmodels.BaseViewModelFactory
import jp.neechan.akari.dictionary.word.domain.usecases.SaveWordUseCase
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