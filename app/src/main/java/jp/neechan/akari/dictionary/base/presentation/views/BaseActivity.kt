package jp.neechan.akari.dictionary.base.presentation.views

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

    protected fun setTitle(title: String) {
        supportActionBar?.title = title
    }

    protected fun setupBackButton() {
        supportActionBar?.apply {
            setDisplayShowHomeEnabled(true)
            setDisplayHomeAsUpEnabled(true)
        }
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