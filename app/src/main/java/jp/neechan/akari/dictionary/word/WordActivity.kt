package jp.neechan.akari.dictionary.word

import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import jp.neechan.akari.dictionary.R
import jp.neechan.akari.dictionary.common.views.BaseActivity
import kotlinx.android.synthetic.main.activity_word.*

class WordActivity : BaseActivity() {

    private lateinit var viewModel: WordViewModel

    companion object {
        const val EXTRA_WORD = "word"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this, viewModelFactory).get(WordViewModel::class.java)

        setContentView(R.layout.activity_word)
        setupObservers()
        setupListeners()
    }

    override fun onStart() {
        super.onStart()
        setupBackButton()
    }

    private fun setupObservers() {
        val word = intent.getStringExtra(EXTRA_WORD)
        if (word != null) {
            viewModel.word = word
            viewModel.wordLiveData.observe(this, Observer {
                wordView.setWord(it)
                progressBar.visibility = GONE
                content.visibility = VISIBLE
            })
        }
    }

    private fun setupListeners() {
        wordView.setSpeakCallback { viewModel.speak() }
    }
}