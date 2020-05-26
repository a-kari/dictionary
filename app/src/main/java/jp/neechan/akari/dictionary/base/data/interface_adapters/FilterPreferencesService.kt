package jp.neechan.akari.dictionary.base.data.interface_adapters

import jp.neechan.akari.dictionary.base.domain.entities.FilterParams

interface FilterPreferencesService {

    suspend fun loadFilterParams(): FilterParams

    suspend fun saveFilterParams(params: FilterParams)
}