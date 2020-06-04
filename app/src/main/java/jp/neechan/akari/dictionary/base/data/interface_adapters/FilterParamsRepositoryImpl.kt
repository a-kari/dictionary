package jp.neechan.akari.dictionary.base.data.interface_adapters

import dagger.Reusable
import jp.neechan.akari.dictionary.base.domain.entities.FilterParams
import jp.neechan.akari.dictionary.base.domain.usecases.FilterParamsRepository
import javax.inject.Inject

@Reusable
class FilterParamsRepositoryImpl @Inject constructor(
    private val preferencesService: FilterPreferencesService) : FilterParamsRepository {

    override suspend fun loadFilterParams(): FilterParams {
        return preferencesService.loadFilterParams()
    }

    override suspend fun saveFilterParams(params: FilterParams) {
        preferencesService.saveFilterParams(params)
    }
}