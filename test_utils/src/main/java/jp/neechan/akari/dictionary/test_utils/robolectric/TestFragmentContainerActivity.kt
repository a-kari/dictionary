package jp.neechan.akari.dictionary.test_utils.robolectric

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import jp.neechan.akari.dictionary.test_utils.R

class TestFragmentContainerActivity : AppCompatActivity() {

    val fragmentContainerId = R.id.fragmentContainer

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_fragment_container)
    }
}
