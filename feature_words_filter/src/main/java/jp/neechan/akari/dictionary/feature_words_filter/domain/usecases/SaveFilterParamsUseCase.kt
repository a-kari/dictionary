package jp.neechan.akari.dictionary.feature_words_filter.domain.usecases

import dagger.Reusable
import jp.neechan.akari.dictionary.core_api.domain.entities.FilterParams
import jp.neechan.akari.dictionary.core_api.domain.usecases.FilterParamsRepository
import jp.neechan.akari.dictionary.core_api.domain.usecases.WordsRepository
import javax.inject.Inject

@Reusable
internal class SaveFilterParamsUseCase @Inject constructor(
    private val paramsRepository: FilterParamsRepository,
    private val wordsRepository: WordsRepository) {

    suspend operator fun invoke(params: FilterParams) {
        paramsRepository.saveFilterParams(params)
        wordsRepository.setAllWordsRecentlyUpdated(true)
    }
}