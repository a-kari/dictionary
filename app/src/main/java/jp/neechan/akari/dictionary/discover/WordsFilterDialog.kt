package jp.neechan.akari.dictionary.discover

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.SeekBar
import jp.neechan.akari.dictionary.R
import jp.neechan.akari.dictionary.common.models.models.Frequency
import jp.neechan.akari.dictionary.common.models.models.PartOfSpeech
import jp.neechan.akari.dictionary.common.utils.toast
import jp.neechan.akari.dictionary.common.views.BaseDialog
import kotlinx.android.synthetic.main.dialog_words_filter.*

class WordsFilterDialog : BaseDialog() {

    // todo: Move the mapping logic to ViewModel.
    private lateinit var frequencies: List<Pair<Frequency, String>>
    private lateinit var partsOfSpeech: List<Pair<PartOfSpeech?, String>>

    companion object {
        fun newInstance() = WordsFilterDialog()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.dialog_words_filter, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // todo: Somehow add "all" PartOfSpeech string when it's null. It will always crash because of !! below.
        frequencies = Frequency.values().map { Pair(it, getString(it.stringResource)) }
        partsOfSpeech = arrayOf(
//            null,
            PartOfSpeech.NOUN,
            PartOfSpeech.PRONOUN,
            PartOfSpeech.ADJECTIVE,
            PartOfSpeech.VERB,
            PartOfSpeech.ADVERB,
            PartOfSpeech.PREPOSITION,
            PartOfSpeech.CONJUNCTION,
            PartOfSpeech.INTERJECTION
        ).map { Pair(it, getString(it!!.stringResource)) }

        setupSeekBar()
        setupSpinner()
        setupFilterButton()
    }

    private fun setupSeekBar() {
        frequencySeekBar.max = frequencies.lastIndex
        frequencySeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                frequencyValueTv.text = frequencies[progress].second
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })
        frequencySeekBar.progress = Frequency.FREQUENT.ordinal
    }

    private fun setupSpinner() {
        partOfSpeechSpinner.adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_dropdown_item,
            partsOfSpeech.map { it.second }
        )
    }

    private fun setupFilterButton() {
        filterButton.setOnClickListener {
            val frequency = frequencies[frequencySeekBar.progress].first
            val partOfSpeech = partsOfSpeech[partOfSpeechSpinner.selectedItemPosition].first
            toast(requireContext(), "Frequency: ${frequency}. Part of speech: $partOfSpeech")
            dismiss()
        }
    }
}