package jp.neechan.akari.dictionary.discover

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import jp.neechan.akari.dictionary.R
import jp.neechan.akari.dictionary.common.views.BaseActivity
import jp.neechan.akari.dictionary.word.WordActivity
import kotlinx.android.synthetic.main.activity_discover.*

class DiscoverActivity : BaseActivity(), WordsAdapter.WordActionListener {

    private lateinit var viewModel: DiscoverViewModel
    private lateinit var wordsAdapter: WordsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_discover)
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
        wordsRv.visibility = View.GONE
        noWordsTv.visibility = View.GONE
        progressBar.visibility = View.VISIBLE
    }

    private fun showEmptyContent() {
        wordsRv.visibility = View.GONE
        progressBar.visibility = View.GONE
        noWordsTv.visibility = View.VISIBLE
    }

    private fun showContent(words: List<String>) {
        wordsAdapter.addWords(words)
        progressBar.visibility = View.GONE
        noWordsTv.visibility = View.GONE
        wordsRv.visibility = View.VISIBLE
    }

    override fun onWordClicked(word: String) {
        val wordIntent = Intent(this, WordActivity::class.java)
        wordIntent.putExtra(WordActivity.EXTRA_WORD, word)
        startActivity(wordIntent)
    }
}
