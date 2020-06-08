package jp.neechan.akari.dictionary.feature_word.presentation

import android.os.Bundle
import androidx.fragment.app.commit
import jp.neechan.akari.dictionary.base_ui.presentation.views.BaseActivity
import jp.neechan.akari.dictionary.feature_word.R
import kotlinx.android.synthetic.main.activity_word.*

class WordActivity : BaseActivity() {

    private lateinit var wordFragment: WordFragment
    private lateinit var word: String

    companion object {
        const val EXTRA_WORD = "word"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        word = intent.getStringExtra(EXTRA_WORD).orEmpty()

        setContentView(R.layout.activity_word)
        setupToolbar()
        setupFragment()
    }

    private fun setupToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.title = word
        setupBackButton()
    }

    private fun setupFragment() {
        wordFragment = WordFragment.newInstance(word)
        supportFragmentManager.commit {
            replace(R.id.content, wordFragment, WordFragment::class.simpleName)
        }
    }
}