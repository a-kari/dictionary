package jp.neechan.akari.dictionary.discover.filter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.SeekBar
import androidx.lifecycle.ViewModelProvider
import jp.neechan.akari.dictionary.R
import jp.neechan.akari.dictionary.common.views.BaseDialog
import kotlinx.android.synthetic.main.dialog_words_filter.*

class WordsFilterDialog : BaseDialog() {

    private lateinit var viewModel: WordsFilterViewModel

    companion object {
        fun newInstance() = WordsFilterDialog()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.dialog_words_filter, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this, viewModelFactory).get(WordsFilterViewModel::class.java)

        setupSeekBar()
        setupSpinner()
        setupFilterButton()
    }

    private fun setupSeekBar() {
        frequencySeekBar.max = viewModel.frequencyStrings.lastIndex
        frequencySeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                frequencyValueTv.setText(viewModel.frequencyStrings[progress])
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })
        frequencySeekBar.progress = viewModel.initialFrequencyIndex
    }

    private fun setupSpinner() {
        partOfSpeechSpinner.adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_dropdown_item,
            viewModel.partOfSpeechStrings.map { getString(it) }
        )
        partOfSpeechSpinner.setSelection(viewModel.initialPartOfSpeechIndex)
    }

    private fun setupFilterButton() {
        filterButton.setOnClickListener {
            val frequencyIndex = frequencySeekBar.progress
            val partOfSpeechIndex = partOfSpeechSpinner.selectedItemPosition
            viewModel.updateWordsFilterParams(frequencyIndex, partOfSpeechIndex)
            dismiss()
        }
    }
}