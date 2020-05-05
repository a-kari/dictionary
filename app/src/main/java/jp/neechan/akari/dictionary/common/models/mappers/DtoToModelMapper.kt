package jp.neechan.akari.dictionary.common.models.mappers

interface DtoToModelMapper<D, M> {

    fun toModel(dto: D): M
}