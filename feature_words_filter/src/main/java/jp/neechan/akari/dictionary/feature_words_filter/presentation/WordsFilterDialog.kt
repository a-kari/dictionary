package jp.neechan.akari.dictionary.feature_words_filter.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.SeekBar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import jp.neechan.akari.dictionary.base_ui.presentation.views.BaseDialog
import jp.neechan.akari.dictionary.core_api.di.AppWithFacade
import jp.neechan.akari.dictionary.feature_words_filter.R
import jp.neechan.akari.dictionary.feature_words_filter.di.FilterComponent
import kotlinx.android.synthetic.main.dialog_words_filter.*
import javax.inject.Inject

internal class WordsFilterDialog : BaseDialog() {

    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: WordsFilterViewModel

    companion object {
        fun newInstance() = WordsFilterDialog()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FilterComponent.create((requireActivity().application as AppWithFacade).getFacade()).inject(this)
        viewModel = ViewModelProvider(this, viewModelFactory).get(WordsFilterViewModel::class.java)
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