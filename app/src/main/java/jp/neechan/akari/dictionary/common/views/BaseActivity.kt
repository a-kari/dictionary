package jp.neechan.akari.dictionary.common.views

import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import jp.neechan.akari.dictionary.R
import jp.neechan.akari.dictionary.common.viewmodels.ViewModelFactory
import org.koin.android.ext.android.inject

abstract class BaseActivity : AppCompatActivity() {

    protected val viewModelFactory: ViewModelFactory by inject()

    override fun onStart() {
        super.onStart()
        setupToolbar()
    }

    private fun setupToolbar() {
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        if (toolbar != null) {
            setSupportActionBar(toolbar)
        }
    }

    protected fun setupBackButton() {
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (item.itemId == android.R.id.home) {
            onBackPressed()
            true

        } else {
            super.onOptionsItemSelected(item)
        }
    }
}