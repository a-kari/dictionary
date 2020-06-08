package jp.neechan.akari.dictionary.feature_search_word.presentation

import android.content.Context
import android.content.Intent
import jp.neechan.akari.dictionary.core_api.presentation.mediators.SearchWordMediator
import javax.inject.Inject

class SearchWordMediatorImpl @Inject constructor() : SearchWordMediator {

    override fun openSearchWord(context: Context) {
        context.startActivity(Intent(context, SearchWordActivity::class.java))
    }
}