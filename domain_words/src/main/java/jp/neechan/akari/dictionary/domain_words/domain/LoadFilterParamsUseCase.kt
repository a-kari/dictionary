package jp.neechan.akari.dictionary.domain_words.domain

import dagger.Reusable
import jp.neechan.akari.dictionary.core_api.domain.entities.FilterParams
import jp.neechan.akari.dictionary.core_api.domain.usecases.FilterParamsRepository
import javax.inject.Inject

@Reusable
class LoadFilterParamsUseCase @Inject constructor(private val paramsRepository: FilterParamsRepository) {

    suspend operator fun invoke(): FilterParams {
        return paramsRepository.loadFilterParams()
    }
}