package jp.neechan.akari.dictionary.domain_words.domain

import dagger.Reusable
import jp.neechan.akari.dictionary.core_api.domain.entities.Result
import jp.neechan.akari.dictionary.core_api.domain.entities.Word
import jp.neechan.akari.dictionary.core_api.domain.usecases.WordsRepository
import javax.inject.Inject

@Reusable
class LoadWordUseCase @Inject constructor(private val wordsRepository: WordsRepository) {

    suspend operator fun invoke(wordId: String): Result<Word> {
        return wordsRepository.loadWord(wordId)
    }
}
