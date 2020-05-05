package jp.neechan.akari.dictionary.common.models.models

data class Word(
    val word: String,
    val pronunciation: String?,
    val syllables: String?,
    val frequency: Frequency,
    val definitions: LinkedHashMap<PartOfSpeech, List<Definition>>?
)