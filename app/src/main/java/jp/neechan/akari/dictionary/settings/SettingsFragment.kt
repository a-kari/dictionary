package jp.neechan.akari.dictionary.settings

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import jp.neechan.akari.dictionary.R
import jp.neechan.akari.dictionary.common.BaseFragment
import jp.neechan.akari.dictionary.word.TextToSpeechHelper
import kotlinx.android.synthetic.main.fragment_settings.*
import java.util.*

class SettingsFragment : BaseFragment() {

    private lateinit var ttsHelper: TextToSpeechHelper

    companion object {
        fun newInstance() = SettingsFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_settings, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ttsHelper = TextToSpeechHelper(requireContext())

        Handler().postDelayed({
            setupObservers()
            setupListeners()
        }, 1000)
    }

    // todo: Get selected values from user preferences.
    private fun setupObservers() {
        val selectedLocale = ttsHelper.loadSelectedLocale()
        val voices = ttsHelper.loadVoiceNames()
        val selectedVoice = ttsHelper.loadSelectedVoiceName()

        if (selectedLocale == Locale.UK) {
            pronunciationUkButton.isChecked = true

        } else {
            pronunciationUsButton.isChecked = true
        }

        if (voices.isNotEmpty()) {
            voicesSpinner.adapter = ArrayAdapter(
                requireContext(),
                android.R.layout.simple_spinner_dropdown_item,
                voices
            )
            voicesSpinner.isEnabled = true
            voicesSpinner.setSelection(voices.indexOf(selectedVoice))

        } else {
            voicesSpinner.isEnabled = false
        }
    }

    private fun setupListeners() {
        voicesSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                ttsHelper.selectVoice(voicesSpinner.selectedItem as String)
                ttsHelper.testSpeak()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }
}