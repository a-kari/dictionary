package jp.neechan.akari.dictionary.feature_word.presentation

import android.content.Context
import android.content.Intent
import androidx.annotation.IdRes
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.commit
import jp.neechan.akari.dictionary.core_api.presentation.mediators.WordMediator
import jp.neechan.akari.dictionary.core_api.presentation.models.WordUI
import javax.inject.Inject

class WordMediatorImpl @Inject constructor() : WordMediator {

    private val wordFragmentTag = WordFragment::class.simpleName

    override fun openWordActivity(context: Context, wordId: String) {
        val openWordIntent = Intent(context, WordActivity::class.java)
        openWordIntent.putExtra(WordActivity.EXTRA_WORD, wordId)
        context.startActivity(openWordIntent)
    }

    override fun openWordFragment(@IdRes containerId: Int, fragmentManager: FragmentManager) {
        val fragment = fragmentManager.findFragmentByTag(wordFragmentTag) ?: WordFragment.newInstance()
        fragmentManager.commit { replace(containerId, fragment, wordFragmentTag) }
    }

    override fun showWordInWordFragment(word: WordUI, fragmentManager: FragmentManager) {
        val fragment = fragmentManager.findFragmentByTag(wordFragmentTag) as WordFragment?
            ?: throw IllegalStateException("Cannot set the word, because WordFragment is not " +
                    "loaded into the FragmentManager. " +
                    "Make sure you call openWordFragment() before showWordInWordFragment()")
        fragment.setWord(word)
    }
}
