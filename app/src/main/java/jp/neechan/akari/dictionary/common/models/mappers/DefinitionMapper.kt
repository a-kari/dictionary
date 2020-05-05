package jp.neechan.akari.dictionary.common.models.mappers

import jp.neechan.akari.dictionary.common.models.dto.DefinitionDto
import jp.neechan.akari.dictionary.common.models.models.Definition

class DefinitionMapper : DtoToModelMapper<DefinitionDto, Definition> {

    override fun toModel(dto: DefinitionDto): Definition {
        return Definition(
            dto.definition,
            dto.examples?.joinToString(separator = "\n", transform = { "\"$it\"" }),
            dto.synonyms?.joinToString(),
            dto.antonyms?.joinToString()
        )
    }
}