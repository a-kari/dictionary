package jp.neechan.akari.dictionary.discover.filter.domain.usecases

import jp.neechan.akari.dictionary.base.domain.entities.FilterParams
import jp.neechan.akari.dictionary.base.domain.usecases.WordsRepository
import jp.neechan.akari.dictionary.base.domain.usecases.FilterParamsRepository

class SaveFilterParamsUseCase(private val paramsRepository: FilterParamsRepository,
                              private val wordsRepository: WordsRepository) {

    suspend operator fun invoke(params: FilterParams) {
        paramsRepository.saveFilterParams(params)
        wordsRepository.setAllWordsRecentlyUpdated(true)
    }
}