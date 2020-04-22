package jp.neechan.akari.dictionary.common

fun getSampleWords(): List<Word> {
    val wordsDto = mutableListOf<WordDTO>()
    wordsDto.add(
        WordDTO(
            "Air",
            "ɜr",
            listOf("a", "ir"),
            Frequency.FREQUENT,
            listOf(
                DefinitionDTO(
                    "expose to fresh air",
                    PartOfSpeech.VERB,
                    null,
                    listOf("aerate", "air out"),
                    null
                ),
                DefinitionDTO(
                    "the mass of air surrounding the Earth",
                    PartOfSpeech.NOUN,
                    listOf("it was exposed to the air"),
                    listOf("atmosphere"),
                    null
                ),
                DefinitionDTO(
                    "travel via aircraft",
                    PartOfSpeech.NOUN,
                    listOf(
                        "air travel involves too much waiting in airports",
                        "if you've time to spare go by air"
                    ),
                    listOf("air travel", "aviation"),
                    null
                ),
                DefinitionDTO(
                    "expose to cool or cold air so as to cool or freshen",
                    PartOfSpeech.VERB,
                    listOf("air the old winter clothes", "air out the smoke-filled rooms"),
                    listOf("air out", "vent", "ventilate"),
                    null
                )
            )
        )
    )
    wordsDto.add(
        WordDTO(
            "Emotional",
            "ɪ'moʊʃənəl",
            listOf("e", "mo", "tion", "al"),
            Frequency.NORMAL,
            listOf(
                DefinitionDTO(
                    "(of persons) excessively affected by emotion",
                    PartOfSpeech.ADJECTIVE,
                    listOf("he would become emotional over nothing at all"),
                    listOf("aroused", "excited", "worked up"),
                    null
                ),
                DefinitionDTO(
                    "of more than usual emotion",
                    PartOfSpeech.ADJECTIVE,
                    listOf("his behavior was highly emotional"),
                    listOf("het up", "hokey", "hot-blooded", "kitschy"),
                    listOf("unemotional")
                )
            )
        )
    )

    wordsDto.add(WordDTO("Rain", null, null, Frequency.NORMAL, null))
    wordsDto.add(WordDTO("Star", null, null, Frequency.NORMAL, null))
    wordsDto.add(WordDTO("Fire", null, null, Frequency.NORMAL, null))
    wordsDto.add(WordDTO("Forest", null, null, Frequency.NORMAL, null))
    wordsDto.add(WordDTO("Lightning", null, null, Frequency.NORMAL, null))
    wordsDto.add(WordDTO("Snow", null, null, Frequency.NORMAL, null))
    wordsDto.add(WordDTO("Mountain", null, null, Frequency.NORMAL, null))
    wordsDto.add(WordDTO("Lake", null, null, Frequency.NORMAL, null))
    wordsDto.add(WordDTO("Ocean", null, null, Frequency.NORMAL, null))
    wordsDto.add(WordDTO("Sea", null, null, Frequency.NORMAL, null))

    val wordMapper = WordMapper(DefinitionMapper())
    return wordsDto.map { wordMapper.dtoToWord(it) }
}