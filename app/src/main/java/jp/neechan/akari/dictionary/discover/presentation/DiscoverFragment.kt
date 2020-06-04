package jp.neechan.akari.dictionary.discover.presentation

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
import androidx.annotation.StringRes
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import jp.neechan.akari.dictionary.App
import jp.neechan.akari.dictionary.R
import jp.neechan.akari.dictionary.base.presentation.adapters.WordsAdapter
import jp.neechan.akari.dictionary.base.presentation.extensions.addVerticalDividers
import jp.neechan.akari.dictionary.base.presentation.models.UIState
import jp.neechan.akari.dictionary.base.presentation.views.BaseFragment
import jp.neechan.akari.dictionary.discover.di.DaggerDiscoverComponent
import jp.neechan.akari.dictionary.filter.presentation.WordsFilterDialog
import jp.neechan.akari.dictionary.word.presentation.WordActivity
import kotlinx.android.synthetic.main.fragment_discover.*
import javax.inject.Inject

class DiscoverFragment : BaseFragment(), WordsAdapter.WordActionListener {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: DiscoverViewModel

    private lateinit var wordsAdapter: WordsAdapter

    companion object {
        fun newInstance() = DiscoverFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DaggerDiscoverComponent.builder()
                               .appComponent((requireActivity().application as App).getAppComponent())
                               .build()
                               .inject(this)
        viewModel = ViewModelProvider(this, viewModelFactory).get(DiscoverViewModel::class.java)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_discover, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupObservers()
    }

    private fun setupRecyclerView() {
        wordsAdapter = WordsAdapter(this)
        wordsRv.adapter = wordsAdapter
        wordsRv.addVerticalDividers()
        wordsRv.setHasFixedSize(true)

        // ItemAnimator is removed, because the animation after filtering takes too much time,
        // so a user sees: 1. the end of loading, 2. old items, 3. new items.
        // It wasn't used anywhere else except for this.
        wordsRv.itemAnimator = null
    }

    private fun setupObservers() {
        viewModel.wordsLiveData.observe(viewLifecycleOwner, Observer { words ->
            wordsAdapter.submitList(words)
        })

        viewModel.uiStateLiveData.observe(viewLifecycleOwner, Observer { state ->
            when (state) {
                is UIState.ShowLoading -> showProgressBar()
                is UIState.ShowContent -> showContent()
                is UIState.ShowNotFoundError -> showEmptyContent()
                is UIState.ShowError -> showError(state.errorMessage)
            }
        })
    }

    private fun showProgressBar() {
        noWordsTv.visibility = GONE
        errorTv.visibility = GONE
        wordsRv.visibility = GONE
        progressBar.visibility = VISIBLE
    }

    private fun showContent() {
        progressBar.visibility = GONE
        noWordsTv.visibility = GONE
        errorTv.visibility = GONE
        wordsRv.visibility = VISIBLE
    }

    private fun showEmptyContent() {
        progressBar.visibility = GONE
        errorTv.visibility = GONE
        wordsRv.visibility = GONE
        noWordsTv.visibility = VISIBLE
    }

    private fun showError(@StringRes errorMessage: Int) {
        errorTv.setText(errorMessage)
        progressBar.visibility = GONE
        noWordsTv.visibility = GONE
        wordsRv.visibility = GONE
        errorTv.visibility = VISIBLE
    }

    override fun onWordClicked(word: String) {
        val wordIntent = Intent(requireContext(), WordActivity::class.java)
        wordIntent.putExtra(WordActivity.EXTRA_WORD, word)
        wordIntent.putExtra(WordActivity.EXTRA_ADD_TO_DICTIONARY_ENABLED, true)
        startActivity(wordIntent)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_filter, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (item.itemId == R.id.filter) {
            showFilterDialog()
            true

        } else {
            return super.onOptionsItemSelected(item)
        }
    }

    private fun showFilterDialog() {
        WordsFilterDialog.newInstance().show(childFragmentManager, WordsFilterDialog::class.simpleName)
    }
}