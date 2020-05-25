package jp.neechan.akari.dictionary.settings.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.annotation.IdRes
import androidx.core.view.children
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import jp.neechan.akari.dictionary.R
import jp.neechan.akari.dictionary.base.presentation.views.BaseFragment
import kotlinx.android.synthetic.main.fragment_settings.*

class SettingsFragment : BaseFragment() {

    private lateinit var viewModel: SettingsViewModel

    private lateinit var voicesAdapter: ArrayAdapter<String>

    companion object {
        fun newInstance() =
            SettingsFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_settings, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this, viewModelFactory).get(SettingsViewModel::class.java)

        setupVoicesSpinner()
        setupObservers()
        setupListeners()
    }

    private fun setupVoicesSpinner() {
        voicesAdapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_dropdown_item,
            mutableListOf()
        )
        voicesSpinner.adapter = voicesAdapter
    }

    private fun setupObservers() {
        createPronunciationRadioButtons(viewModel.pronunciationNames)
        pronunciationsRadioGroup.checkPosition(viewModel.preferredPronunciationIndex)

        viewModel.voiceNamesLiveData.observe(viewLifecycleOwner, Observer { voices ->
            if (voices.isNotEmpty()) {
                voicesAdapter.clear()
                voicesAdapter.addAll(voices)
                voicesSpinner.setSelection(viewModel.preferredVoiceIndex)
                voicesSpinner.isEnabled = true

            } else {
                voicesSpinner.isEnabled = false
            }
        })
    }

    private fun setupListeners() {
        pronunciationsRadioGroup.setOnCheckedChangeListener { _, checkedId ->
            viewModel.selectPronunciation(pronunciationsRadioGroup.getCheckedPosition(checkedId))
        }

        voicesSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                viewModel.selectVoice(position)
                viewModel.testSpeak()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }

    private fun createPronunciationRadioButtons(pronunciations: List<String>) {
        pronunciations.forEach { pronunciation ->
            val radioButton = RadioButton(requireContext())
            radioButton.text = pronunciation
            pronunciationsRadioGroup.addView(radioButton)
        }
    }

    private fun RadioGroup.checkPosition(position: Int) {
        check(getChildAt(position).id)
    }

    private fun RadioGroup.getCheckedPosition(@IdRes checkedId: Int): Int {
        return children.indexOfFirst { it.id == checkedId }
    }
}