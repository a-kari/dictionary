package jp.neechan.akari.dictionary.base.domain.usecases

import dagger.Reusable
import jp.neechan.akari.dictionary.base.domain.entities.Result
import jp.neechan.akari.dictionary.base.domain.entities.Word
import javax.inject.Inject

@Reusable
class LoadWordUseCase @Inject constructor(private val wordsRepository: WordsRepository) {

    suspend operator fun invoke(wordId: String): Result<Word> {
        return wordsRepository.loadWord(wordId)
    }
}