package jp.neechan.akari.dictionary.feature_discover.presentation

import androidx.annotation.IdRes
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.commit
import jp.neechan.akari.dictionary.core_api.presentation.mediators.DiscoverMediator
import javax.inject.Inject

class DiscoverMediatorImpl @Inject constructor() : DiscoverMediator {

    override fun openDiscover(@IdRes containerId: Int, fragmentManager: FragmentManager) {
        val tag = DiscoverFragment::class.simpleName
        val fragment = fragmentManager.findFragmentByTag(tag) ?: DiscoverFragment.newInstance()
        fragmentManager.commit { replace(containerId, fragment, tag) }
    }
}
