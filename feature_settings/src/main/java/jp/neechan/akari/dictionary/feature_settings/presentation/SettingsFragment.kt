package jp.neechan.akari.dictionary.feature_settings.presentation

import android.os.Bundle
import android.os.Handler
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
import jp.neechan.akari.dictionary.base_ui.presentation.views.BaseFragment
import jp.neechan.akari.dictionary.core_api.di.AppWithFacade
import jp.neechan.akari.dictionary.feature_settings.R
import jp.neechan.akari.dictionary.feature_settings.di.SettingsComponent
import kotlinx.android.synthetic.main.fragment_settings.*
import javax.inject.Inject

class SettingsFragment : BaseFragment() {

    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: SettingsViewModel

    private lateinit var voicesAdapter: ArrayAdapter<String>

    private lateinit var pronunciationChangedListener: RadioGroup.OnCheckedChangeListener
    private lateinit var voiceChangedListener: AdapterView.OnItemSelectedListener

    companion object {
        fun newInstance() = SettingsFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        SettingsComponent.create((requireActivity().application as AppWithFacade).getFacade()).inject(this)
        viewModel = ViewModelProvider(this, viewModelFactory).get(SettingsViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_settings, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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
        viewModel.pronunciationNames.observe(viewLifecycleOwner, Observer { pronunciations ->
            createPronunciationRadioButtons(pronunciations)
        })

        viewModel.preferredPronunciationIndex.observe(viewLifecycleOwner, Observer { preferredPronunciationIndex ->
            pronunciationsRadioGroup.setOnCheckedChangeListener(null)
            pronunciationsRadioGroup.checkPosition(preferredPronunciationIndex)
            pronunciationsRadioGroup.setOnCheckedChangeListener(pronunciationChangedListener)
        })

        viewModel.voices.observe(viewLifecycleOwner, Observer { voices ->
            voicesAdapter.clear()

            if (voices.isNotEmpty()) {
                voicesAdapter.addAll(voices)
                voicesSpinner.isEnabled = true

            } else {
                voicesSpinner.isEnabled = false
            }
        })

        viewModel.preferredVoiceIndex.observe(viewLifecycleOwner, Observer { preferredVoiceIndex ->
            voicesSpinner.onItemSelectedListener = null
            voicesSpinner.setSelection(preferredVoiceIndex, false)
            Handler().post { voicesSpinner.onItemSelectedListener = voiceChangedListener }
            viewModel.speak(getString(R.string.settings_voice_test_phrase))
        })
    }

    private fun setupListeners() {
        pronunciationChangedListener = RadioGroup.OnCheckedChangeListener { _, checkedId ->
            viewModel.savePreferredPronunciation(pronunciationsRadioGroup.getCheckedPosition(checkedId))
        }
        pronunciationsRadioGroup.setOnCheckedChangeListener(pronunciationChangedListener)

        voiceChangedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                viewModel.savePreferredVoice(position)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
        voicesSpinner.onItemSelectedListener = voiceChangedListener
    }

    private fun createPronunciationRadioButtons(pronunciations: List<String>) {
        pronunciationsRadioGroup.removeAllViews()
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