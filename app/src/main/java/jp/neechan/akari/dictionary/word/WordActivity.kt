package jp.neechan.akari.dictionary.word

import android.os.Bundle
import jp.neechan.akari.dictionary.R
import jp.neechan.akari.dictionary.common.BaseActivity
import jp.neechan.akari.dictionary.common.Word
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
    }

    override fun onStart() {
        super.onStart()
        setupBackButton()
    }

    private fun setupFragment() {
        wordFragment = WordFragment.newInstance()
        supportFragmentManager.beginTransaction()
                              .replace(R.id.content, wordFragment, WordFragment::class.simpleName)
                              .commit()
    }

    override fun onResume() {
        super.onResume()
        val addToDictionaryEnabled = intent.getBooleanExtra(EXTRA_ADD_TO_DICTIONARY_ENABLED, false)
        wordFragment.setAddToDictionaryEnabled(addToDictionaryEnabled)
        setupObservers()
    }

    // todo: Should receive not a Word instance, but a String (which is word's id)
    // todo: and get the needed Word instance from a repository.
    private fun setupObservers() {
        val word = intent.getSerializableExtra(EXTRA_WORD) as Word?
        if (word != null) {
            toolbar.title = word.word
            wordFragment.setWord(word)
        }
    }
}