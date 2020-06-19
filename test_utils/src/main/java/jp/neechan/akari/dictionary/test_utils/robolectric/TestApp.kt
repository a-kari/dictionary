package jp.neechan.akari.dictionary.test_utils.robolectric

import android.app.Application
import jp.neechan.akari.dictionary.test_utils.R

/** Sets an AppCompat theme for the app. */
class TestApp : Application() {

    override fun onCreate() {
        super.onCreate()
        setTheme(R.style.AppTheme)
    }
}