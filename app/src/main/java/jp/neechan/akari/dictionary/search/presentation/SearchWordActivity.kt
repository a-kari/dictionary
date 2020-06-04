package jp.neechan.akari.dictionary.search.presentation

import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.annotation.StringRes
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.commit
import androidx.lifecycle.Observer
import jp.neechan.akari.dictionary.R
import jp.neechan.akari.dictionary.base.presentation.models.UIState
import jp.neechan.akari.dictionary.base.presentation.models.WordUI
import jp.neechan.akari.dictionary.base.presentation.views.BaseActivity
import jp.neechan.akari.dictionary.word.presentation.WordFragment
import kotlinx.android.synthetic.main.activity_search_word.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchWordActivity : BaseActivity() {

    private val viewModel: SearchWordViewModel by viewModel()
    private lateinit var wordFragment: WordFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
        viewModel.wordLiveData.observe(this, Observer { state ->
            when (state) {
                is UIState.ShowLoading -> showProgressBar()
                is UIState.ShowContent -> showContent(state.content)
                is UIState.ShowNotFoundError -> showEmptyContent()
                is UIState.ShowError -> showError(state.errorMessage)
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
        errorTv.visibility = GONE
        content.visibility = GONE
        hintTv.visibility = VISIBLE
    }

    private fun showProgressBar() {
        hintTv.visibility = GONE
        emptyContentTv.visibility = GONE
        errorTv.visibility = GONE
        content.visibility = GONE
        progressBar.visibility = VISIBLE
    }

    private fun showEmptyContent() {
        progressBar.visibility = GONE
        hintTv.visibility = GONE
        errorTv.visibility = GONE
        content.visibility = GONE
        emptyContentTv.visibility = VISIBLE
    }

    private fun showContent(word: WordUI) {
        wordFragment.setWord(word)

        progressBar.visibility = GONE
        hintTv.visibility = GONE
        emptyContentTv.visibility = GONE
        errorTv.visibility = GONE
        content.visibility = VISIBLE
        searchView.clearFocus()
    }

    private fun showError(@StringRes errorMessage: Int) {
        errorTv.setText(errorMessage)
        progressBar.visibility = GONE
        hintTv.visibility = GONE
        emptyContentTv.visibility = GONE
        content.visibility = GONE
        errorTv.visibility = VISIBLE
    }
}