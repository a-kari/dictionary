package jp.neechan.akari.dictionary.word

class DefinitionMapper {

    fun dtoToDefinition(dto: DefinitionDTO): Definition {
        return Definition(
            dto.definition,
            dto.examples?.joinToString(separator = "\n", transform = { "\"$it\"" }),
            dto.synonyms?.joinToString(),
            dto.antonyms?.joinToString()
        )
    }
}