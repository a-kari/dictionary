package jp.neechan.akari.dictionary.core_api.domain.usecases

import jp.neechan.akari.dictionary.core_api.domain.entities.FilterParams

interface FilterParamsRepository {

    suspend fun loadFilterParams(): FilterParams

    suspend fun saveFilterParams(params: FilterParams)
}
