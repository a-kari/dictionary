package jp.neechan.akari.dictionary.feature_word.presentation

import android.content.Intent
import androidx.test.core.app.ApplicationProvider
import jp.neechan.akari.dictionary.test_utils.robolectric.TestApp
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric.buildActivity
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(application = TestApp::class, shadows = [ShadowWordFragment::class])
class WordActivityTest {

    @Test
    fun `should set ActionBar title`() {
        val expectedTitle = "Our expected title"
        val activityUnderTest = buildActivity(
            WordActivity::class.java,
            Intent(ApplicationProvider.getApplicationContext(), WordActivity::class.java).apply {
                putExtra(WordActivity.EXTRA_WORD, expectedTitle)
            }
        ).setup().get()

        assertNotNull(activityUnderTest.supportActionBar)
        assertEquals(expectedTitle, activityUnderTest.supportActionBar!!.title)
    }
}