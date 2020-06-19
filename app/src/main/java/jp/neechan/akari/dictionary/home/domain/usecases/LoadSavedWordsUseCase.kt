package jp.neechan.akari.dictionary.home.domain.usecases

import dagger.Reusable
import jp.neechan.akari.dictionary.base.domain.entities.FilterParams
import jp.neechan.akari.dictionary.base.domain.entities.Page
import jp.neechan.akari.dictionary.base.domain.entities.Result
import jp.neechan.akari.dictionary.base.domain.usecases.LoadWordsUseCase
import jp.neechan.akari.dictionary.base.domain.usecases.WordsRepository
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import javax.inject.Inject

@Reusable
class LoadSavedWordsUseCase @Inject constructor(private val wordsRepository: WordsRepository) : LoadWordsUseCase {

    override suspend operator fun invoke(params: FilterParams): Result<Page<String>> {
        val words = wordsRepository.loadSavedWords(params)
        wordsRepository.setSavedWordsRecentlyUpdated(false)
        return words
    }

    override fun observeRecentlyUpdated(): ConflatedBroadcastChannel<Boolean> {
        return wordsRepository.savedWordsRecentlyUpdated
    }
}