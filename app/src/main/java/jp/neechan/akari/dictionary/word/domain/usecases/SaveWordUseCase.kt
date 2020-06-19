package jp.neechan.akari.dictionary.word.domain.usecases

import dagger.Reusable
import jp.neechan.akari.dictionary.base.domain.entities.Word
import jp.neechan.akari.dictionary.base.domain.usecases.WordsRepository
import javax.inject.Inject

@Reusable
class SaveWordUseCase @Inject constructor(private val wordsRepository: WordsRepository) {

    suspend operator fun invoke(word: Word) {
        wordsRepository.saveWord(word)
        wordsRepository.setSavedWordsRecentlyUpdated(true)
    }
}