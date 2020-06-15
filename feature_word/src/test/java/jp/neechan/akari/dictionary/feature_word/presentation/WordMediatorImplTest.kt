package jp.neechan.akari.dictionary.feature_word.presentation

import android.content.Intent
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.commit
import jp.neechan.akari.dictionary.core_api.presentation.models.WordUI
import jp.neechan.akari.dictionary.test_utils.robolectric.TestActivity
import jp.neechan.akari.dictionary.test_utils.robolectric.TestFragmentContainerActivity
import org.hamcrest.Matchers.instanceOf
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertThat
import org.junit.Assert.assertTrue
import org.junit.Assert.fail
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.`when`
import org.mockito.Mockito.anyString
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.robolectric.Robolectric.buildActivity
import org.robolectric.RobolectricTestRunner
import org.robolectric.Shadows.shadowOf
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(shadows = [ShadowWordFragment::class])
class WordMediatorImplTest {

    private val mediatorUnderTest = WordMediatorImpl()

    @Test
    fun `should open WordActivity and pass a word there`() {
        val inputWord = "hello"
        val activity = buildActivity(TestActivity::class.java).create().get()
        val expectedIntent = Intent(activity, WordActivity::class.java).apply {
            putExtra(WordActivity.EXTRA_WORD, inputWord)
        }

        mediatorUnderTest.openWordActivity(activity, inputWord)
        val actualIntent = shadowOf(activity).peekNextStartedActivity()

        assertEquals(expectedIntent.component, actualIntent.component)
        assertEquals(
            expectedIntent.extras!!.getString(WordActivity.EXTRA_WORD),
            actualIntent.extras!!.getString(WordActivity.EXTRA_WORD)
        )
    }

    @Test
    fun `should load a new WordFragment into a fragment container`() {
        val activity = buildActivity(TestFragmentContainerActivity::class.java).setup().get()

        mediatorUnderTest.openWordFragment(activity.fragmentContainerId, activity.supportFragmentManager)
        val actualFragment = activity.supportFragmentManager.findFragmentById(activity.fragmentContainerId)

        assertThat(actualFragment, instanceOf(WordFragment::class.java))
    }

    @Test
    fun `should reload existing WordFragment into a fragment container`() {
        val activity = buildActivity(TestFragmentContainerActivity::class.java).setup().get()
        activity.supportFragmentManager.commit {
            replace(activity.fragmentContainerId,
                WordFragment.newInstance(),
                WordFragment::class.simpleName
            )
        }
        val expectedCreatedTimes = 1

        mediatorUnderTest.openWordFragment(activity.fragmentContainerId, activity.supportFragmentManager)
        val actualFragment = activity.supportFragmentManager.findFragmentById(activity.fragmentContainerId)

        // Check that an existing WordFragment instance is reloaded into the container.
        assertThat(actualFragment, instanceOf(WordFragment::class.java))
        assertEquals(expectedCreatedTimes, ShadowWordFragment.createdTimes)
    }

    @Test
    fun `should call WordFragment's setWord()`() {
        val mockWordUI = mock(WordUI::class.java)
        val mockWordFragment = mock(WordFragment::class.java)
        val mockFragmentManager = mock(FragmentManager::class.java)
        `when`(mockFragmentManager.findFragmentByTag(anyString())).thenReturn(mockWordFragment)

        mediatorUnderTest.showWordInWordFragment(mockWordUI, mockFragmentManager)

        verify(mockWordFragment).setWord(mockWordUI)
    }

    @Test
    fun `should throw a "Cannot set the word" exception()`() {
        val mockWordUI = mock(WordUI::class.java)
        val mockFragmentManager = mock(FragmentManager::class.java)

        try {
            mediatorUnderTest.showWordInWordFragment(mockWordUI, mockFragmentManager)
            fail("\"Cannot set the word\" exception was expected, but it didn't occur")

        } catch (t: Throwable) {
            assertThat(t, instanceOf(IllegalStateException::class.java))
            assertTrue(t.message!!.contains("Cannot set the word", true))
        }
    }

    @After
    fun teardown() {
        ShadowWordFragment.teardownCreatedTimes()
    }
}