package jp.neechan.akari.dictionary.word

import android.os.Bundle
import androidx.fragment.app.commit
import jp.neechan.akari.dictionary.R
import jp.neechan.akari.dictionary.common.views.BaseActivity
import kotlinx.android.synthetic.main.activity_word.*

class WordActivity : BaseActivity() {

    private lateinit var wordFragment: WordFragment
    private lateinit var word: String
    private var addToDictionaryEnabled = false

    companion object {
        const val EXTRA_WORD = "word"
        const val EXTRA_ADD_TO_DICTIONARY_ENABLED = "addToDictionaryEnabled"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        word = intent.getStringExtra(EXTRA_WORD).orEmpty()
        addToDictionaryEnabled = intent.getBooleanExtra(EXTRA_ADD_TO_DICTIONARY_ENABLED, false)

        setContentView(R.layout.activity_word)
        setupFragment()
    }

    override fun onStart() {
        super.onStart()
        toolbar.title = word
        setupBackButton()
    }

    private fun setupFragment() {
        wordFragment = WordFragment.newInstance(word, addToDictionaryEnabled)
        supportFragmentManager.commit {
            replace(R.id.content, wordFragment, WordFragment::class.simpleName)
        }
    }
}