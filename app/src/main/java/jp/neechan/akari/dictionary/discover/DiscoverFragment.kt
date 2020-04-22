package jp.neechan.akari.dictionary.discover

import android.content.Intent
import android.os.Bundle
import android.view.*
import jp.neechan.akari.dictionary.R
import jp.neechan.akari.dictionary.common.*
import jp.neechan.akari.dictionary.word.WordActivity
import kotlinx.android.synthetic.main.fragment_discover.*

class DiscoverFragment : BaseFragment(), WordsAdapter.WordActionListener {

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
        setupRecyclerView()
        setupObservers()
    }

    private fun setupRecyclerView() {
        wordsAdapter = WordsAdapter(this)
        wordsRv.adapter = wordsAdapter
        wordsRv.addVerticalDividers()
    }

    private fun setupObservers() {
        wordsAdapter.addWords(getSampleWords())
    }

    override fun onWordClicked(word: Word) {
        val wordIntent = Intent(requireContext(), WordActivity::class.java)
        wordIntent.putExtra(WordActivity.EXTRA_WORD, word)
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