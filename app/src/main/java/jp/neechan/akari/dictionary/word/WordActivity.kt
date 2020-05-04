package jp.neechan.akari.dictionary.word

import android.os.Bundle
import jp.neechan.akari.dictionary.R
import jp.neechan.akari.dictionary.common.views.BaseActivity
import kotlinx.android.synthetic.main.activity_word.*

class WordActivity : BaseActivity() {

    private lateinit var wordFragment: WordFragment

    companion object {
        const val EXTRA_WORD = "word"
        const val EXTRA_ADD_TO_DICTIONARY_ENABLED = "addToDictionaryEnabled"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_word)
        setupFragment()
        setupObservers()
    }

    override fun onStart() {
        super.onStart()
        setupBackButton()
    }

    private fun setupFragment() {
        wordFragment = WordFragment.newInstance()
        val addToDictionaryEnabled = intent.getBooleanExtra(EXTRA_ADD_TO_DICTIONARY_ENABLED, false)
        wordFragment.setAddToDictionaryEnabled(addToDictionaryEnabled)

        supportFragmentManager.beginTransaction()
                              .replace(R.id.content, wordFragment, WordFragment::class.simpleName)
                              .commit()
    }

    // todo: Should receive not a Word instance, but a String (which is word's id)
    // todo: and get the needed Word instance from a repository.
    private fun setupObservers() {
        val word = intent.getStringExtra(EXTRA_WORD)
        if (word != null) {
            toolbar.title = word
            wordFragment.setWord(
                Word(
                    word,
                    word,
                    null,
                    Frequency.NORMAL,
                    null
                )
            )
        }
    }
}