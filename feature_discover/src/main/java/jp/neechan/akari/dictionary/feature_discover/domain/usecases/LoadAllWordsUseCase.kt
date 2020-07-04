package jp.neechan.akari.dictionary.feature_discover.domain.usecases

import dagger.Reusable
import jp.neechan.akari.dictionary.core_api.domain.entities.FilterParams
import jp.neechan.akari.dictionary.core_api.domain.entities.Page
import jp.neechan.akari.dictionary.core_api.domain.entities.Result
import jp.neechan.akari.dictionary.core_api.domain.usecases.WordsRepository
import jp.neechan.akari.dictionary.domain_words.domain.LoadWordsUseCase
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import javax.inject.Inject

@Reusable
internal class LoadAllWordsUseCase @Inject constructor(
    private val wordsRepository: WordsRepository
) : LoadWordsUseCase {

    override suspend operator fun invoke(params: FilterParams): Result<Page<String>> {
        val words = wordsRepository.loadWords(params)
        wordsRepository.setAllWordsRecentlyUpdated(false)
        return words
    }

    override fun observeRecentlyUpdated(): ConflatedBroadcastChannel<Boolean> {
        return wordsRepository.allWordsRecentlyUpdated
    }
}
