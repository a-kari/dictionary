package jp.neechan.akari.dictionary.common.models.mappers

interface LayerMapper<L, H> {

    fun mapToHigherLayer(lowerLayerEntity: L): H

    fun mapToLowerLayer(higherLevelEntity: H): L
}