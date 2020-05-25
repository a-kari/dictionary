package jp.neechan.akari.dictionary.word.domain.usecases

import jp.neechan.akari.dictionary.base.domain.entities.Word
import jp.neechan.akari.dictionary.base.domain.usecases.WordsRepository

class SaveWordUseCase(private val wordsRepository: WordsRepository) {

    suspend operator fun invoke(word: Word) {
        wordsRepository.saveWord(word)
        wordsRepository.setSavedWordsRecentlyUpdated(true)
    }
}