package jp.neechan.akari.dictionary.base.domain.entities

data class FilterParams(var page: Int = DEFAULT_PAGE,
                        var partOfSpeech: PartOfSpeech = DEFAULT_PART_OF_SPEECH,
                        var frequency: Frequency = DEFAULT_FREQUENCY) {

    companion object {
        const val DEFAULT_PAGE = 1
        const val DEFAULT_PAGE_SIZE = 100
        val DEFAULT_PART_OF_SPEECH = PartOfSpeech.NOUN
        val DEFAULT_FREQUENCY = Frequency.FREQUENT
    }
}