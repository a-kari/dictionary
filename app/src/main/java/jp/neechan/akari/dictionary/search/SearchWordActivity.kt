package jp.neechan.akari.dictionary.search

import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.commit
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import jp.neechan.akari.dictionary.R
import jp.neechan.akari.dictionary.common.models.models.Result
import jp.neechan.akari.dictionary.common.models.models.Word
import jp.neechan.akari.dictionary.common.utils.toast
import jp.neechan.akari.dictionary.common.views.BaseActivity
import jp.neechan.akari.dictionary.word.WordFragment
import kotlinx.android.synthetic.main.activity_search_word.*

class SearchWordActivity : BaseActivity() {

    private lateinit var viewModel: SearchWordViewModel
    private lateinit var wordFragment: WordFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this, viewModelFactory).get(SearchWordViewModel::class.java)

        setContentView(R.layout.activity_search_word)
        setupWordFragment()
        showHint()

        setupObservers()
        setupListeners()
    }

    override fun onStart() {
        super.onStart()
        setupBackButton()
    }

    override fun onResume() {
        super.onResume()
        searchView.requestFocus()
    }

    private fun setupWordFragment() {
        wordFragment = WordFragment.newInstance(true)
        supportFragmentManager.commit {
            replace(R.id.content, wordFragment, wordFragment.javaClass.simpleName)
        }
    }
    
    private fun setupObservers() {
        viewModel.resultLiveData.observe(this, Observer { result ->
            when (result) {
                is Result.Loading -> showProgressBar()
                is Result.Success -> showContent(result.value)
                is Result.NotFoundError -> showEmptyContent()
                is Result.Error -> showError(result)
            }
        })
    }

    private fun setupListeners() {
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                viewModel.searchWord(query)
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })
    }

    private fun showHint() {
        progressBar.visibility = GONE
        emptyContentTv.visibility = GONE
        content.visibility = GONE
        hintTv.visibility = VISIBLE
    }

    private fun showProgressBar() {
        hintTv.visibility = GONE
        emptyContentTv.visibility = GONE
        content.visibility = GONE
        progressBar.visibility = VISIBLE
    }

    private fun showEmptyContent() {
        progressBar.visibility = GONE
        hintTv.visibility = GONE
        content.visibility = GONE
        emptyContentTv.visibility = VISIBLE
    }

    private fun showContent(word: Word) {
        wordFragment.setWord(word)

        progressBar.visibility = GONE
        hintTv.visibility = GONE
        emptyContentTv.visibility = GONE
        content.visibility = VISIBLE
        searchView.clearFocus()
    }

    private fun showError(error: Result.Error) {
        toast(this, error.errorMessage)
    }
}