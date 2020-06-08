package jp.neechan.akari.dictionary.feature_home.presentation

import androidx.annotation.IdRes
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.commit
import jp.neechan.akari.dictionary.core_api.presentation.mediators.HomeMediator
import javax.inject.Inject

class HomeMediatorImpl @Inject constructor() : HomeMediator {

    override fun openHome(@IdRes containerId: Int, fragmentManager: FragmentManager) {
        val tag = HomeFragment::class.simpleName
        val fragment = fragmentManager.findFragmentByTag(tag) ?: HomeFragment.newInstance()
        fragmentManager.commit { replace(containerId, fragment, tag) }
    }
}