package jp.neechan.akari.dictionary.word

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import jp.neechan.akari.dictionary.R
import jp.neechan.akari.dictionary.common.models.models.Result
import jp.neechan.akari.dictionary.common.models.models.Word
import jp.neechan.akari.dictionary.common.utils.isValid
import jp.neechan.akari.dictionary.common.utils.toast
import jp.neechan.akari.dictionary.common.views.BaseFragment
import kotlinx.android.synthetic.main.fragment_word.*

class WordFragment : BaseFragment() {

    private lateinit var viewModel: WordViewModel
    private lateinit var word: String
    private var addToDictionaryEnabled = false

    companion object {
        private const val ARGUMENT_WORD = "word"
        private const val ARGUMENT_ADD_TO_DICTIONARY_ENABLED = "addToDictionaryEnabled"

        fun newInstance(word: String, addToDictionaryEnabled: Boolean): WordFragment {
            val arguments = Bundle().apply {
                putString(ARGUMENT_WORD, word)
                putBoolean(ARGUMENT_ADD_TO_DICTIONARY_ENABLED, addToDictionaryEnabled)
            }
            return WordFragment().apply {
                setArguments(arguments)
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_word, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (arguments != null) {
            viewModel = ViewModelProvider(this, viewModelFactory).get(WordViewModel::class.java)
            requireArguments().let { arguments ->
                word = arguments.getString(ARGUMENT_WORD).orEmpty()
                addToDictionaryEnabled = arguments.getBoolean(ARGUMENT_ADD_TO_DICTIONARY_ENABLED)
            }

            if (word.isValid()) {
                setupObservers()
                setupListeners()
                maybeShowAddToDictionaryButton()
            }
        }
    }

    private fun maybeShowAddToDictionaryButton() {
        if (addToDictionaryEnabled) {
            addToDictionaryButton.visibility = VISIBLE
            addToDictionaryButton.setOnClickListener { toast(requireContext(), "Adding to your dictionary...") }

        } else {
            addToDictionaryButton.visibility = GONE
        }
    }

    private fun setupObservers() {
        viewModel.word = word
        viewModel.wordLiveData.observe(viewLifecycleOwner, Observer { result ->
            when (result) {
                is Result.Success -> showContent(result.value)
                is Result.Error -> showError(result)
            }
        })
    }

    private fun showContent(word: Word) {
        wordView.setWord(word)
        progressBar.visibility = GONE
        content.visibility = VISIBLE
    }

    private fun showError(error: Result.Error) {
        toast(requireContext(), error.errorMessage)
    }

    private fun setupListeners() {
        wordView.setSpeakCallback { viewModel.speak() }
    }
}