package jp.neechan.akari.dictionary.core_api.presentation.mediators

import androidx.annotation.IdRes
import androidx.fragment.app.FragmentManager

interface DiscoverMediator {

    fun openDiscover(@IdRes containerId: Int, fragmentManager: FragmentManager)
}
