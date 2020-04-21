package jp.neechan.akari.dictionary

import android.os.Bundle
import androidx.fragment.app.Fragment
import jp.neechan.akari.dictionary.common.BaseActivity
import jp.neechan.akari.dictionary.discover.DiscoverFragment
import jp.neechan.akari.dictionary.home.HomeFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupBottomNavigation()
    }

    // todo: Maybe I should make a fabric here?
    private fun setupBottomNavigation() {
        bottomNavigationView.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.home -> openHome()
                else -> openDiscover()
            }
            true
        }
        bottomNavigationView.selectedItemId = R.id.home
    }

    private fun openHome() {
        toolbar.setTitle(R.string.main_bottom_navigation_home)
        loadFragment(HomeFragment.newInstance())
    }

    private fun openDiscover() {
        toolbar.setTitle(R.string.main_bottom_navigation_discover)
        loadFragment(DiscoverFragment.newInstance())
    }

    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment, fragment.javaClass.simpleName)
            .commit()
    }
}
