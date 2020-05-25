package jp.neechan.akari.dictionary.home.domain.usecases

import jp.neechan.akari.dictionary.base.domain.usecases.WordsRepository

class DeleteWordUseCase(private val wordsRepository: WordsRepository) {

    suspend operator fun invoke(word: String) {
        wordsRepository.deleteWord(word)
        wordsRepository.setSavedWordsRecentlyUpdated(true)
    }
}