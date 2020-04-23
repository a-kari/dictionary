package jp.neechan.akari.dictionary.word

import android.os.Bundle
import jp.neechan.akari.dictionary.R
import jp.neechan.akari.dictionary.common.BaseActivity
import jp.neechan.akari.dictionary.common.Word
import kotlinx.android.synthetic.main.activity_word.*

class WordActivity : BaseActivity() {

    companion object {
        const val EXTRA_WORD = "word"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_word)
        setupObservers()
    }

    override fun onStart() {
        super.onStart()
        setupBackButton()
    }

    // todo: Should retrieve not a Word instance, but a String (which is word's id)
    // todo: and get the needed Word instance from a repository.
    private fun setupObservers() {
        val word = intent.getSerializableExtra(EXTRA_WORD) as Word?
        if (word != null) {
            toolbar.title = word.word
            wordView.setWord(word)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        wordView.destroy()
    }
}