package jp.neechan.akari.dictionary.common

fun getSampleWords(): List<Word> {
    val words = mutableListOf<Word>()
    words.add(Word("Air", "", listOf(), listOf(), 10.0))
    words.add(Word("Nature", "", listOf(), listOf(), 10.0))
    words.add(Word("Cold", "", listOf(), listOf(), 10.0))
    words.add(Word("Rain", "", listOf(), listOf(), 10.0))
    words.add(Word("Star", "", listOf(), listOf(), 10.0))
    words.add(Word("Fire", "", listOf(), listOf(), 10.0))
    words.add(Word("Forest", "", listOf(), listOf(), 10.0))
    words.add(Word("Lightning", "", listOf(), listOf(), 10.0))
    words.add(Word("Snow", "", listOf(), listOf(), 10.0))
    words.add(Word("Mountain", "", listOf(), listOf(), 10.0))
    words.add(Word("Lake", "", listOf(), listOf(), 10.0))
    words.add(Word("Ocean", "", listOf(), listOf(), 10.0))
    words.add(Word("Sea", "", listOf(), listOf(), 10.0))
    return words
}