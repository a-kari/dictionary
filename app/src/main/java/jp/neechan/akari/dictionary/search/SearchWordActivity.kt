package jp.neechan.akari.dictionary.search

import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.appcompat.widget.SearchView
import jp.neechan.akari.dictionary.R
import jp.neechan.akari.dictionary.common.models.models.Frequency
import jp.neechan.akari.dictionary.common.models.models.Word
import jp.neechan.akari.dictionary.common.views.BaseActivity
import jp.neechan.akari.dictionary.word.WordFragment
import kotlinx.android.synthetic.main.activity_search_word.*

class SearchWordActivity : BaseActivity() {

    private lateinit var wordFragment: WordFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_word)
        setupSearchView()
    }

    override fun onStart() {
        super.onStart()
        setupBackButton()
    }

    private fun setupSearchView() {
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                when {
                    query.isBlank() -> showHint()
                    query.length < 3 -> showEmptyContent()
                    else -> showContent(Word(query, query, null, Frequency.NORMAL, null))
                }
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })
    }

    private fun showHint() {
        emptyContentTv.visibility = GONE
        content.visibility = GONE
        hintTv.visibility = VISIBLE
    }

    private fun showEmptyContent() {
        hintTv.visibility = GONE
        content.visibility = GONE
        emptyContentTv.visibility = VISIBLE
    }

    private fun showContent(word: Word) {
        if (!::wordFragment.isInitialized) {
            wordFragment = WordFragment.newInstance(word.word, true)
            supportFragmentManager.beginTransaction()
                                  .replace(R.id.content, wordFragment, wordFragment.javaClass.simpleName)
                                  .commit()
        }

        hintTv.visibility = GONE
        emptyContentTv.visibility = GONE
        content.visibility = VISIBLE
        searchView.clearFocus()
    }

    override fun onResume() {
        super.onResume()
        searchView.requestFocus()
    }
}