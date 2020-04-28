package jp.neechan.akari.dictionary

import android.os.Bundle
import androidx.fragment.app.Fragment
import jp.neechan.akari.dictionary.common.BaseActivity
import jp.neechan.akari.dictionary.discover.DiscoverFragment
import jp.neechan.akari.dictionary.home.HomeFragment
import jp.neechan.akari.dictionary.settings.SettingsFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupBottomNavigation()
    }

    private fun setupBottomNavigation() {
        bottomNavigationView.setOnNavigationItemSelectedListener { menuItem ->
            toolbar.title = menuItem.title
            when (menuItem.itemId) {
                R.id.home -> openHome()
                R.id.discover -> openDiscover()
                else -> openSettings()
            }
            true
        }
        bottomNavigationView.selectedItemId = R.id.home
    }

    private fun openHome() {
        loadFragment(HomeFragment.newInstance())
    }

    private fun openDiscover() {
        loadFragment(DiscoverFragment.newInstance())
    }

    private fun openSettings() {
        loadFragment(SettingsFragment.newInstance())
    }

    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
                              .replace(R.id.fragmentContainer, fragment, fragment.javaClass.simpleName)
                              .commit()
    }
}
