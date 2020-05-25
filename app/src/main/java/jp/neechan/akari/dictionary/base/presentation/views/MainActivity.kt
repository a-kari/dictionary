package jp.neechan.akari.dictionary.base.presentation.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import jp.neechan.akari.dictionary.R
import jp.neechan.akari.dictionary.discover.presentation.DiscoverFragment
import jp.neechan.akari.dictionary.home.presentation.HomeFragment
import jp.neechan.akari.dictionary.settings.presentation.SettingsFragment
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
                R.id.discover -> loadFragment(DiscoverFragment::class.java)
                R.id.settings -> loadFragment(SettingsFragment::class.java)
                else -> loadFragment(HomeFragment::class.java)
            }
            true
        }
        bottomNavigationView.selectedItemId =
            R.id.home
    }

    private fun <T : Fragment> loadFragment(clazz: Class<T>) {
        val tag = clazz.simpleName
        val fragment = supportFragmentManager.findFragmentByTag(tag) ?: when (clazz) {
            DiscoverFragment::class.java -> DiscoverFragment.newInstance()
            SettingsFragment::class.java -> SettingsFragment.newInstance()
            else -> HomeFragment.newInstance()
        }

        supportFragmentManager.commit { replace(R.id.fragmentContainer, fragment, tag) }
    }
}