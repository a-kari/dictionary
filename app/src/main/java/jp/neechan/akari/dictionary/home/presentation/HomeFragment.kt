package jp.neechan.akari.dictionary.home.presentation

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import jp.neechan.akari.dictionary.R
import jp.neechan.akari.dictionary.base.domain.entities.Result
import jp.neechan.akari.dictionary.base.presentation.extensions.addVerticalDividers
import jp.neechan.akari.dictionary.base.presentation.extensions.toast
import jp.neechan.akari.dictionary.base.presentation.views.BaseFragment
import jp.neechan.akari.dictionary.home.search.presentation.SearchWordActivity
import jp.neechan.akari.dictionary.word.presentation.WordActivity
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : BaseFragment(), EditableWordsAdapter.WordActionListener {

    private lateinit var viewModel: HomeViewModel
    private lateinit var wordsAdapter: EditableWordsAdapter
    private lateinit var editButton: MenuItem

    companion object {
        fun newInstance() = HomeFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this, viewModelFactory).get(HomeViewModel::class.java)

        setupRecyclerView()
        setupObservers()
        setupListeners()
    }

    private fun setupRecyclerView() {
        wordsAdapter = EditableWordsAdapter(this)
        wordsRv.adapter = wordsAdapter
        wordsRv.addVerticalDividers()
        wordsRv.itemAnimator = null
    }

    private fun setupObservers() {
        viewModel.wordsLiveData.observe(viewLifecycleOwner, Observer { words ->
            wordsAdapter.submitList(words)
        })

        viewModel.wordsStatusLiveData.observe(viewLifecycleOwner, Observer { status ->
            when (status) {
                is Result.Loading -> showProgressBar()
                is Result.Success -> showContent()
                is Result.NotFoundError -> showEmptyContent()
                is Result.Error -> showError(status)
            }
        })
    }

    private fun showProgressBar() {
        noWordsTv.visibility = GONE
        wordsRv.visibility = GONE
        progressBar.visibility = VISIBLE
    }

    private fun showContent() {
        progressBar.visibility = GONE
        noWordsTv.visibility = GONE
        wordsRv.visibility = VISIBLE
    }

    private fun showEmptyContent() {
        noWordsTv.visibility = VISIBLE
        progressBar.visibility = GONE
        wordsRv.visibility = GONE
    }

    private fun showError(error: Result.Error) {
        requireContext().toast(error.errorMessage)
    }

    private fun setupListeners() {
        addWordButton.setOnClickListener {
            val searchIntent = Intent(requireContext(), SearchWordActivity::class.java)
            startActivity(searchIntent)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_edit, menu)
        super.onCreateOptionsMenu(menu, inflater)

        editButton = menu.findItem(R.id.edit)
        syncEditMode()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (item.itemId == R.id.edit) {
            viewModel.isEditMode = !viewModel.isEditMode
            syncEditMode()
            true

        } else {
            super.onOptionsItemSelected(item)
        }
    }

    private fun syncEditMode() {
        wordsAdapter.toggleEditMode(viewModel.isEditMode)
        editButton.setIcon(if (!viewModel.isEditMode) R.drawable.ic_edit else R.drawable.ic_ok)
    }

    override fun onWordClicked(word: String) {
        val wordIntent = Intent(requireContext(), WordActivity::class.java)
        wordIntent.putExtra(WordActivity.EXTRA_WORD, word)
        wordIntent.putExtra(WordActivity.EXTRA_ADD_TO_DICTIONARY_ENABLED, false)
        startActivity(wordIntent)
    }

    override fun onWordDeleted(word: String) {
        viewModel.deleteWord(word)
    }
}