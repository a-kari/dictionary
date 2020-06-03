package jp.neechan.akari.dictionary.discover.filter.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.SeekBar
import androidx.lifecycle.Observer
import jp.neechan.akari.dictionary.R
import jp.neechan.akari.dictionary.base.presentation.views.BaseDialog
import kotlinx.android.synthetic.main.dialog_words_filter.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class WordsFilterDialog : BaseDialog() {

    private val viewModel: WordsFilterViewModel by viewModel()

    companion object {
        fun newInstance() = WordsFilterDialog()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.dialog_words_filter, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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

        viewModel.preferredFrequencyIndex.observe(viewLifecycleOwner, Observer { preferredFrequencyIndex ->
            frequencySeekBar.progress = preferredFrequencyIndex
        })
    }

    private fun setupSpinner() {
        partOfSpeechSpinner.adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_dropdown_item,
            viewModel.partOfSpeechStrings.map { getString(it) }
        )

        viewModel.preferredPartOfSpeechIndex.observe(viewLifecycleOwner, Observer { preferredPartOfSpeechIndex ->
            partOfSpeechSpinner.setSelection(preferredPartOfSpeechIndex)
        })
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