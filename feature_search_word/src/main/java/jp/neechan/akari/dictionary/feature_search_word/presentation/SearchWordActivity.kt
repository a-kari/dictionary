package jp.neechan.akari.dictionary.feature_search_word.presentation

import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.annotation.StringRes
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import jp.neechan.akari.dictionary.base_ui.presentation.views.BaseActivity
import jp.neechan.akari.dictionary.core_api.presentation.models.UIState
import jp.neechan.akari.dictionary.core_api.presentation.models.WordUI
import jp.neechan.akari.dictionary.feature_search_word.R
import kotlinx.android.synthetic.main.activity_search_word.*
import javax.inject.Inject

internal class SearchWordActivity : BaseActivity() {

    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: SearchWordViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this, viewModelFactory).get(SearchWordViewModel::class.java)

        setContentView(R.layout.activity_search_word)
        setupToolbar()
        setupWordFragment()
        showHint()

        setupObservers()
        setupListeners()
    }

    private fun setupToolbar() {
        setSupportActionBar(toolbar)
        setupBackButton()
    }

    override fun onResume() {
        super.onResume()
        searchView.requestFocus()
    }

    private fun setupWordFragment() {
        wordMediator.openWordFragment(R.id.content, supportFragmentManager)
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
        wordMediator.showWordInWordFragment(word, supportFragmentManager)

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