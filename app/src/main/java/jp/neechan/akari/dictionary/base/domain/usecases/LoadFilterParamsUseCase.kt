package jp.neechan.akari.dictionary.base.domain.usecases

import jp.neechan.akari.dictionary.base.domain.entities.FilterParams

class LoadFilterParamsUseCase(private val paramsRepository: FilterParamsRepository) {

    suspend operator fun invoke(): FilterParams {
        return paramsRepository.loadFilterParams()
    }
}