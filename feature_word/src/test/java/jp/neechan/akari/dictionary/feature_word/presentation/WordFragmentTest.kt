package jp.neechan.akari.dictionary.feature_word.presentation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.testing.launchFragmentInContainer
import jp.neechan.akari.dictionary.core_api.presentation.models.FrequencyUI
import jp.neechan.akari.dictionary.core_api.presentation.models.WordUI
import jp.neechan.akari.dictionary.test_utils.robolectric.TestAppWithMockFacade
import kotlinx.android.synthetic.main.fragment_word.view.*
import kotlinx.android.synthetic.main.view_word.view.*
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Test
import org.junit.experimental.runners.Enclosed
import org.junit.runner.RunWith
import org.robolectric.ParameterizedRobolectricTestRunner
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import java.util.Date

@RunWith(Enclosed::class)
internal class WordFragmentTest {

    @RunWith(RobolectricTestRunner::class)
    @Config(shadows = [ShadowWordFragment::class])
    class NonParameterizedTest {
        @Test
        fun `should return WordFragment with a word argument`() {
            val expectedWord = "hello"

            val fragmentUnderTest = WordFragment.newInstance(expectedWord)

            assertNotNull(fragmentUnderTest.arguments)
            assertEquals(expectedWord, fragmentUnderTest.requireArguments().getString("wordId"))
        }

        @Test
        fun `should return WordFragment with an empty word argument`() {
            val expectedWord = ""

            val fragmentUnderTest = WordFragment.newInstance()

            assertNotNull(fragmentUnderTest.arguments)
            assertEquals(expectedWord, fragmentUnderTest.requireArguments().getString("wordId"))
        }
    }

    @RunWith(ParameterizedRobolectricTestRunner::class)
    @Config(application = TestAppWithMockFacade::class)
    class ParameterizedTest(
        private val inputWordUI: WordUI,
        private val expectedWordTvText: String,
        private val expectedAddToDictionaryButtonVisibility: Int) {

        @Test
        fun `should populate view with the word and show or hide addToDictionaryButton`() {
            val scenario = launchFragmentInContainer<WordFragment>(Bundle())
            scenario.onFragment { fragmentUnderTest ->
                fragmentUnderTest.setWord(inputWordUI)
                val rootView = fragmentUnderTest.view!!
                val actualWordTvText = rootView.wordTv.text
                val actualAddToDictionaryButtonVisibility = rootView.addToDictionaryButton.visibility

                assertEquals(expectedWordTvText, actualWordTvText)
                assertEquals(expectedAddToDictionaryButtonVisibility, actualAddToDictionaryButtonVisibility)
            }
        }

        companion object {
            @JvmStatic
            @ParameterizedRobolectricTestRunner.Parameters
            fun getParams(): Collection<Array<out Any?>> {
                return listOf(
                    arrayOf(
                        WordUI(
                            "hello",
                            "həˈləʊ",
                            "hel-lo",
                            FrequencyUI.FREQUENT,
                            null,
                            null,
                            false
                        ),
                        "hello",
                        View.VISIBLE
                    ),

                    arrayOf(
                        WordUI(
                            "answer",
                            "'ænsər",
                            "an-swer",
                            FrequencyUI.NORMAL,
                            null,
                            Date(),
                            true
                        ),
                        "answer",
                        View.GONE
                    )
                )
            }
        }
    }
}