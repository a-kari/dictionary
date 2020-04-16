package jp.neechan.akari.dictionary.common

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
}