package jp.neechan.akari.dictionary.filter.domain.usecases

import dagger.Reusable
import jp.neechan.akari.dictionary.base.domain.entities.FilterParams
import jp.neechan.akari.dictionary.base.domain.usecases.FilterParamsRepository
import jp.neechan.akari.dictionary.base.domain.usecases.WordsRepository
import javax.inject.Inject

@Reusable
class SaveFilterParamsUseCase @Inject constructor(
    private val paramsRepository: FilterParamsRepository,
    private val wordsRepository: WordsRepository) {

    suspend operator fun invoke(params: FilterParams) {
        paramsRepository.saveFilterParams(params)
        wordsRepository.setAllWordsRecentlyUpdated(true)
    }
}