package jp.neechan.akari.dictionary.common

import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import jp.neechan.akari.dictionary.R

abstract class BaseActivity : AppCompatActivity() {

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