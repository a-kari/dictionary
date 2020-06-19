package jp.neechan.akari.dictionary.base.domain.usecases

import dagger.Reusable
import jp.neechan.akari.dictionary.base.domain.entities.FilterParams
import javax.inject.Inject

@Reusable
class LoadFilterParamsUseCase @Inject constructor(private val paramsRepository: FilterParamsRepository) {

    suspend operator fun invoke(): FilterParams {
        return paramsRepository.loadFilterParams()
    }
}