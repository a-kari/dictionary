package jp.neechan.akari.dictionary.base.domain.usecases

import jp.neechan.akari.dictionary.base.domain.entities.FilterParams

interface FilterParamsRepository {

    suspend fun loadFilterParams(): FilterParams

    suspend fun saveFilterParams(params: FilterParams)
}