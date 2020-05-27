package jp.neechan.akari.dictionary.word.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import jp.neechan.akari.dictionary.R
import jp.neechan.akari.dictionary.base.domain.entities.Result
import jp.neechan.akari.dictionary.base.presentation.extensions.toast
import jp.neechan.akari.dictionary.base.presentation.models.WordUI
import jp.neechan.akari.dictionary.base.presentation.views.BaseFragment
import kotlinx.android.synthetic.main.fragment_word.*

class WordFragment : BaseFragment() {

    private lateinit var viewModel: WordViewModel

    private lateinit var wordId: String
    private var addToDictionaryEnabled = false

    companion object {
        private const val ARGUMENT_WORD_ID = "wordId"
        private const val ARGUMENT_ADD_TO_DICTIONARY_ENABLED = "addToDictionaryEnabled"

        fun newInstance(wordId: String, addToDictionaryEnabled: Boolean): WordFragment {
            val arguments = Bundle().apply {
                putString(ARGUMENT_WORD_ID, wordId)
                putBoolean(ARGUMENT_ADD_TO_DICTIONARY_ENABLED, addToDictionaryEnabled)
            }
            return WordFragment().apply {
                setArguments(arguments)
            }
        }

        fun newInstance(addToDictionaryEnabled: Boolean): WordFragment {
            return newInstance("", addToDictionaryEnabled)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_word, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this, viewModelFactory).get(WordViewModel::class.java)

        requireArguments().let { arguments ->
            wordId = arguments.getString(ARGUMENT_WORD_ID).orEmpty()
            addToDictionaryEnabled = arguments.getBoolean(ARGUMENT_ADD_TO_DICTIONARY_ENABLED)
        }

        // Load a word if its id is passed. Else wait until a word is set from the outside.
        if (wordId.isNotBlank()) {
            setupObservers()
            setupListeners()
        }
    }

    private fun setupObservers() {
        viewModel.wordId = wordId
        viewModel.wordLiveData.observe(viewLifecycleOwner, Observer { result ->
            when (result) {
                is Result.Success -> showContent(result.value)
                is Result.Error -> showError(result)
            }
        })
    }

    private fun setupListeners() {
        wordView.setSpeakCallback { viewModel.speak() }
    }

    private fun showContent(word: WordUI) {
        wordView.setWord(word)
        maybeShowAddToDictionaryButton(!word.isSaved && addToDictionaryEnabled, word)

        progressBar.visibility = GONE
        content.visibility = VISIBLE
    }

    private fun maybeShowAddToDictionaryButton(show: Boolean, word: WordUI) {
        if (show) {
            addToDictionaryButton.visibility = VISIBLE
            addToDictionaryButton.setOnClickListener {
                addToDictionaryButton.visibility = GONE
                viewModel.saveWord(word)
                requireContext().toast(R.string.word_added_to_dictionary)
            }

        } else {
            addToDictionaryButton.visibility = GONE
        }
    }

    private fun showError(error: Result.Error) {
        requireContext().toast(error.errorMessage)
    }

    fun setWord(word: WordUI) {
        viewModel.wordId = word.word
        showContent(word)
        setupListeners()
    }
}