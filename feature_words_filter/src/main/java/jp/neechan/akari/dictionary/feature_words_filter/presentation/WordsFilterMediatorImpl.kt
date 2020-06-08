package jp.neechan.akari.dictionary.feature_words_filter.presentation

import androidx.fragment.app.FragmentManager
import jp.neechan.akari.dictionary.core_api.presentation.mediators.WordsFilterMediator
import javax.inject.Inject

class WordsFilterMediatorImpl @Inject constructor() : WordsFilterMediator {

    override fun openFilter(fragmentManager: FragmentManager) {
        WordsFilterDialog.newInstance().show(fragmentManager, WordsFilterDialog::class.simpleName)
    }
}