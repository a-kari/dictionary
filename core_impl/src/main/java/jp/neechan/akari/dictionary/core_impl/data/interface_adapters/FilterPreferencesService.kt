package jp.neechan.akari.dictionary.core_impl.data.interface_adapters

import jp.neechan.akari.dictionary.core_api.domain.entities.FilterParams

internal interface FilterPreferencesService {

    suspend fun loadFilterParams(): FilterParams

    suspend fun saveFilterParams(params: FilterParams)
}