package jp.neechan.akari.dictionary.common.models.mappers

import jp.neechan.akari.dictionary.common.models.dto.PartOfSpeechDto
import jp.neechan.akari.dictionary.common.models.models.PartOfSpeech

class PartOfSpeechMapper : DtoToModelMapper<PartOfSpeechDto, PartOfSpeech> {

    override fun toModel(dto: PartOfSpeechDto): PartOfSpeech {
        return PartOfSpeech.valueOf(dto.name)
    }
}