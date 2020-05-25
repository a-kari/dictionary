package jp.neechan.akari.dictionary.base.data.interface_adapters

import jp.neechan.akari.dictionary.base.data.framework.dto.FilterParamsDto

interface FilterPreferencesService {

    suspend fun loadFilterParams(): FilterParamsDto

    suspend fun saveFilterParams(params: FilterParamsDto)
}