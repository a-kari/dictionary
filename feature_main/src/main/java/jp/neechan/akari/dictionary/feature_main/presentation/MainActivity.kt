package jp.neechan.akari.dictionary.feature_main.presentation

import android.os.Bundle
import jp.neechan.akari.dictionary.base_ui.presentation.views.BaseActivity
import jp.neechan.akari.dictionary.feature_main.R
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

internal class MainActivity : BaseActivity() {


    companion object {
        private const val SELECTED_NAVIGATION_ITEM_ID = "selectedNavigationItemId"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        setupToolbar()
        setupBottomNavigation()
        selectFragment(savedInstanceState)
    }

    private fun setupToolbar() {
        setSupportActionBar(toolbar)
    }

    private fun setupBottomNavigation() {
        bottomNavigationView.setOnNavigationItemSelectedListener { menuItem ->
            supportActionBar?.title = menuItem.title

            when (menuItem.itemId) {
                R.id.discover -> discoverMediator.openDiscover(R.id.fragmentContainer, supportFragmentManager)
                R.id.settings -> settingsMediator.openSettings(R.id.fragmentContainer, supportFragmentManager)
                else -> homeMediator.openHome(R.id.fragmentContainer, supportFragmentManager)
            }
            true
        }
    }

    private fun selectFragment(savedInstanceState: Bundle?) {
        bottomNavigationView.selectedItemId = savedInstanceState?.getInt(
            SELECTED_NAVIGATION_ITEM_ID,
            R.id.home
        ) ?: R.id.home
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putInt(SELECTED_NAVIGATION_ITEM_ID, bottomNavigationView.selectedItemId)
        super.onSaveInstanceState(outState)
    }
}