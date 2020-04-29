package jp.neechan.akari.dictionary.discover

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import jp.neechan.akari.dictionary.R
import jp.neechan.akari.dictionary.common.BaseFragment
import jp.neechan.akari.dictionary.common.WordsAdapter
import jp.neechan.akari.dictionary.common.addVerticalDividers
import jp.neechan.akari.dictionary.word.WordActivity
import kotlinx.android.synthetic.main.fragment_discover.*

class DiscoverFragment : BaseFragment(), WordsAdapter.WordActionListener {

    private lateinit var viewModel: DiscoverViewModel
    private lateinit var wordsAdapter: WordsAdapter

    companion object {
        fun newInstance() = DiscoverFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_discover, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this, viewModelFactory).get(DiscoverViewModel::class.java)

        setupRecyclerView()
        setupObservers()
    }

    private fun setupRecyclerView() {
        wordsAdapter = WordsAdapter(this)
        wordsRv.adapter = wordsAdapter
        wordsRv.addVerticalDividers()
    }

    // todo: Need to handle errors here when I write a Result<T, Error> class.
    private fun setupObservers() {
        viewModel.words.observe(this, Observer { page ->
            when {
                page == null -> showProgressBar()
                page.content.isEmpty() -> showEmptyContent()
                else -> showContent(page.content)
            }
        })
    }

    private fun showProgressBar() {
        wordsRv.visibility = GONE
        noWordsTv.visibility = GONE
        progressBar.visibility = VISIBLE
    }

    private fun showEmptyContent() {
        wordsRv.visibility = GONE
        progressBar.visibility = GONE
        noWordsTv.visibility = VISIBLE
    }

    private fun showContent(words: List<String>) {
        wordsAdapter.addWords(words)
        progressBar.visibility = GONE
        noWordsTv.visibility = GONE
        wordsRv.visibility = VISIBLE
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