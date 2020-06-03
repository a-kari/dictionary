package jp.neechan.akari.dictionary.base.domain.usecases

import jp.neechan.akari.dictionary.base.domain.entities.Result
import jp.neechan.akari.dictionary.base.domain.entities.Word

class LoadWordUseCase(private val wordsRepository: WordsRepository) {

    suspend operator fun invoke(wordId: String): Result<Word> {
        return wordsRepository.loadWord(wordId)
    }
}