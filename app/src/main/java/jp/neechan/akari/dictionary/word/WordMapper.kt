package jp.neechan.akari.dictionary.word

class WordMapper(private val definitionMapper: DefinitionMapper) {

    fun dtoToWord(dto: WordDTO): Word {
        var definitions: LinkedHashMap<PartOfSpeech, List<Definition>>? = null
        if (dto.definitions != null) {
            definitions = LinkedHashMap()
            for (definitionDTO in dto.definitions) {
                val partOfSpeechDefinitions = definitions[definitionDTO.partOfSpeech] as MutableList<Definition>? ?: mutableListOf()
                partOfSpeechDefinitions.add(definitionMapper.dtoToDefinition(definitionDTO))
                definitions[definitionDTO.partOfSpeech] = partOfSpeechDefinitions
            }
        }

        return Word(
            dto.word,
            dto.pronunciation,
            dto.syllables?.joinToString(separator = "-"),
            dto.frequency,
            definitions
        )
    }
}