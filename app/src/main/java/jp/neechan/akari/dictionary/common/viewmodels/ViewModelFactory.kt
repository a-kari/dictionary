package jp.neechan.akari.dictionary.common.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import jp.neechan.akari.dictionary.common.services.TextToSpeechService
import jp.neechan.akari.dictionary.discover.DiscoverViewModel
import jp.neechan.akari.dictionary.discover.WordsRemoteRepository
import jp.neechan.akari.dictionary.discover.filter.WordsFilterViewModel
import jp.neechan.akari.dictionary.search.SearchWordViewModel
import jp.neechan.akari.dictionary.settings.SettingsViewModel
import jp.neechan.akari.dictionary.word.WordViewModel

class ViewModelFactory(
    private val wordsRemoteRepository: WordsRemoteRepository,
    private val ttsService: TextToSpeechService
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when (modelClass) {
            SearchWordViewModel::class.java -> SearchWordViewModel(wordsRemoteRepository)
            DiscoverViewModel::class.java -> DiscoverViewModel(wordsRemoteRepository)
            WordsFilterViewModel::class.java -> WordsFilterViewModel(wordsRemoteRepository)
            WordViewModel::class.java -> WordViewModel(wordsRemoteRepository, ttsService)
            SettingsViewModel::class.java -> SettingsViewModel(ttsService)
            else -> throw RuntimeException("Cannot instantiate ViewModel: $modelClass")
        } as T
    }
}