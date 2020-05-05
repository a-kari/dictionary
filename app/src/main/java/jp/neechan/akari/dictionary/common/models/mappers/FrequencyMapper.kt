package jp.neechan.akari.dictionary.common.models.mappers

import jp.neechan.akari.dictionary.common.models.dto.FrequencyDto
import jp.neechan.akari.dictionary.common.models.models.Frequency

class FrequencyMapper : DtoToModelMapper<FrequencyDto, Frequency> {

    override fun toModel(dto: FrequencyDto): Frequency {
        return Frequency.valueOf(dto.name)
    }
}