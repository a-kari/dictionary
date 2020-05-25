package jp.neechan.akari.dictionary.base.data.interface_adapters

import jp.neechan.akari.dictionary.base.data.framework.dto.FilterParamsDto
import jp.neechan.akari.dictionary.base.domain.entities.mappers.ModelMapper
import jp.neechan.akari.dictionary.base.domain.entities.FilterParams
import jp.neechan.akari.dictionary.base.domain.usecases.FilterParamsRepository

class FilterParamsRepositoryImpl(private val preferencesService: FilterPreferencesService,
                                 private val paramsMapper: ModelMapper<FilterParams, FilterParamsDto>) : FilterParamsRepository {

    override suspend fun loadFilterParams(): FilterParams {
        return paramsMapper.mapToInternalLayer(preferencesService.loadFilterParams())
    }

    override suspend fun saveFilterParams(params: FilterParams) {
        preferencesService.saveFilterParams(paramsMapper.mapToExternalLayer(params))
    }
}