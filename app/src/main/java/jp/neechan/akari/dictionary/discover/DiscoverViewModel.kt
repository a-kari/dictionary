package jp.neechan.akari.dictionary.discover

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel

class DiscoverViewModel(private val wordsRemoteRepository: WordsRemoteRepository) : ViewModel() {

    private val ioScope = CoroutineScope(Dispatchers.IO + SupervisorJob())

    val words by lazy {
        wordsRemoteRepository.subscribeToWords(ioScope)
    }

    val wordsError by lazy {
        wordsRemoteRepository.subscribeToWordsError()
    }

    override fun onCleared() {
        super.onCleared()
        ioScope.cancel()
    }
}