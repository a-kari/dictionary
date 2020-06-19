package jp.neechan.akari.dictionary.home.domain.usecases

import dagger.Reusable
import jp.neechan.akari.dictionary.base.domain.usecases.WordsRepository
import javax.inject.Inject

@Reusable
class DeleteWordUseCase @Inject constructor(private val wordsRepository: WordsRepository) {

    suspend operator fun invoke(word: String) {
        wordsRepository.deleteWord(word)
        wordsRepository.setSavedWordsRecentlyUpdated(true)
    }
}