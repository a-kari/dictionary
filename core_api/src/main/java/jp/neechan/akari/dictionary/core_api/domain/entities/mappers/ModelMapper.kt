package jp.neechan.akari.dictionary.core_api.domain.entities.mappers

interface ModelMapper<INT, EXT> {

    fun mapToInternalLayer(externalLayerModel: EXT): INT

    fun mapToExternalLayer(internalLayerModel: INT): EXT
}