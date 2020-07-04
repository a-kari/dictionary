package jp.neechan.akari.dictionary.core_api.presentation.mediators

import android.content.Context
import androidx.annotation.IdRes
import androidx.fragment.app.FragmentManager
import jp.neechan.akari.dictionary.core_api.presentation.models.WordUI

interface WordMediator {

    fun openWordActivity(context: Context, wordId: String)

    fun openWordFragment(@IdRes containerId: Int, fragmentManager: FragmentManager)

    fun showWordInWordFragment(word: WordUI, fragmentManager: FragmentManager)
}
