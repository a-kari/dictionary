package jp.neechan.akari.dictionary.feature_word.domain.usecases

import dagger.Reusable
import jp.neechan.akari.dictionary.core_api.domain.entities.Word
import jp.neechan.akari.dictionary.core_api.domain.usecases.WordsRepository
import javax.inject.Inject

@Reusable
class SaveWordUseCase @Inject constructor(private val wordsRepository: WordsRepository) {

    suspend operator fun invoke(word: Word) {
        wordsRepository.saveWord(word)
        wordsRepository.setSavedWordsRecentlyUpdated(true)
    }
}