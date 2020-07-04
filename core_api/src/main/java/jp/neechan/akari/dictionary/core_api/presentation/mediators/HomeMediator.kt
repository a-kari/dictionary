package jp.neechan.akari.dictionary.core_api.presentation.mediators

import androidx.annotation.IdRes
import androidx.fragment.app.FragmentManager

interface HomeMediator {

    fun openHome(@IdRes containerId: Int, fragmentManager: FragmentManager)
}
