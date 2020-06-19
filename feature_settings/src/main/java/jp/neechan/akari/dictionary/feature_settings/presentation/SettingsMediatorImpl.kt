package jp.neechan.akari.dictionary.feature_settings.presentation

import androidx.annotation.IdRes
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.commit
import jp.neechan.akari.dictionary.core_api.presentation.mediators.SettingsMediator
import javax.inject.Inject

class SettingsMediatorImpl @Inject constructor() : SettingsMediator {

    override fun openSettings(@IdRes containerId: Int, fragmentManager: FragmentManager) {
        val tag = SettingsFragment::class.simpleName
        val fragment = fragmentManager.findFragmentByTag(tag) ?: SettingsFragment.newInstance()
        fragmentManager.commit { replace(containerId, fragment, tag) }
    }
}