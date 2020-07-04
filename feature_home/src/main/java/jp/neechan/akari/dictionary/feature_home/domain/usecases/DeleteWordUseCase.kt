package jp.neechan.akari.dictionary.feature_home.domain.usecases

import dagger.Reusable
import jp.neechan.akari.dictionary.core_api.domain.usecases.WordsRepository
import javax.inject.Inject

@Reusable
internal class DeleteWordUseCase @Inject constructor(private val wordsRepository: WordsRepository) {

    suspend operator fun invoke(word: String) {
        wordsRepository.deleteWord(word)
        wordsRepository.setSavedWordsRecentlyUpdated(true)
    }
}
