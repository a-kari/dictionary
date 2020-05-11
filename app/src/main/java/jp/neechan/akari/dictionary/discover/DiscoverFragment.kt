package jp.neechan.akari.dictionary.discover

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import jp.neechan.akari.dictionary.R
import jp.neechan.akari.dictionary.common.models.models.Result
import jp.neechan.akari.dictionary.common.utils.addVerticalDividers
import jp.neechan.akari.dictionary.common.utils.toast
import jp.neechan.akari.dictionary.common.views.BaseFragment
import jp.neechan.akari.dictionary.discover.filter.WordsFilterDialog
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
        wordsRv.setHasFixedSize(true)
    }

    private fun setupObservers() {
        viewModel.words.observe(viewLifecycleOwner, Observer { words ->
            wordsAdapter.submitList(words)
            showContent()
        })

        viewModel.wordsError.observe(viewLifecycleOwner, Observer { error ->
            when (error) {
                is Result.NotFoundError -> showEmptyContent()
                is Result.Error -> showError(error.errorMessageResource)
            }
        })
    }

    private fun showContent() {
        progressBar.visibility = View.GONE
        noWordsTv.visibility = View.GONE
        wordsRv.visibility = View.VISIBLE
    }

    private fun showEmptyContent() {
        noWordsTv.visibility = View.VISIBLE
        progressBar.visibility = View.GONE
        wordsRv.visibility = View.GONE
    }

    private fun showError(errorMessageResource: Int) {
        toast(requireContext(), errorMessageResource)
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