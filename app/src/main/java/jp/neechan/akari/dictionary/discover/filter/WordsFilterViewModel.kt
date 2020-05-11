package jp.neechan.akari.dictionary.discover.filter

import androidx.lifecycle.ViewModel
import jp.neechan.akari.dictionary.R
import jp.neechan.akari.dictionary.common.models.models.Frequency
import jp.neechan.akari.dictionary.common.models.models.PartOfSpeech
import jp.neechan.akari.dictionary.discover.WordsRemoteRepository

class WordsFilterViewModel(private val wordsRemoteRepository: WordsRemoteRepository) : ViewModel() {

    private val frequencies = Frequency.values()
    private val partsOfSpeech = arrayOf(
        null, // PartOfSpeech.ALL. I.e. don't filter by part of speech.
        PartOfSpeech.NOUN,
        PartOfSpeech.PRONOUN,
        PartOfSpeech.ADJECTIVE,
        PartOfSpeech.VERB,
        PartOfSpeech.ADVERB,
        PartOfSpeech.PREPOSITION,
        PartOfSpeech.CONJUNCTION
    )

    val frequencyStrings = frequencies.map { it.stringResource }
    val partOfSpeechStrings = partsOfSpeech.map { it?.stringResource ?: R.string.part_of_speech_all }

    val initialFrequencyIndex = frequencies.indexOf(wordsRemoteRepository.wordsFilterFrequency)
    val initialPartOfSpeechIndex = partsOfSpeech.indexOf(wordsRemoteRepository.wordsFilterPartOfSpeech)

    fun updateWordsFilterParams(frequencyIndex: Int, partOfSpeechIndex: Int) {
        val frequency = frequencies[frequencyIndex]
        val partOfSpeech = partsOfSpeech[partOfSpeechIndex]
        wordsRemoteRepository.updateWordsFilterParams(frequency, partOfSpeech)
    }
}