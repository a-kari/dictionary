package jp.neechan.akari.dictionary.feature_word.presentation

import jp.neechan.akari.dictionary.core_api.presentation.models.DefinitionUI
import jp.neechan.akari.dictionary.core_api.presentation.models.FrequencyUI
import jp.neechan.akari.dictionary.core_api.presentation.models.PartOfSpeechUI
import jp.neechan.akari.dictionary.core_api.presentation.models.WordUI
import jp.neechan.akari.dictionary.test_utils.robolectric.TestActivity
import kotlinx.android.synthetic.main.view_word.view.*
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.experimental.runners.Enclosed
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.robolectric.ParameterizedRobolectricTestRunner
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import java.util.Date

@RunWith(Enclosed::class)
class WordViewTest {

    @RunWith(RobolectricTestRunner::class)
    class NonParameterizedTest {

        // It's a workaround for the Robolectric issue: https://github.com/robolectric/robolectric/issues/5076
        // Tl;dr: Robolectric causes Mockito crash on verifying a mocked lambda.
        private interface UnitFunction: () -> Unit

        @Test
        fun `should call speakCallback on speakButton click`() {
            val activity = Robolectric.buildActivity(TestActivity::class.java).create().get()
            val viewUnderTest = WordView(activity)
            val mockSpeakCallback = mock(UnitFunction::class.java) as () -> Unit
            val wordUI = WordUI("hello", "həˈləʊ", "hel-lo", FrequencyUI.FREQUENT, null, Date())

            viewUnderTest.setSpeakCallback(mockSpeakCallback)
            viewUnderTest.setWord(wordUI)

            viewUnderTest.speakButton.performClick()

            verify(mockSpeakCallback)()
        }
    }

    @RunWith(ParameterizedRobolectricTestRunner::class)
    class ParameterizedTest(
        private val inputWordUI: WordUI,
        private val expectedWordText: String,
        private val expectedPronunciationText: String,
        private val expectedSyllablesText: String,
        private val expectedFrequencyText: String,
        private val expectedDefinitionsCount: Int) {

        @Test
        fun `should populate view with the word`() {
            val activity = Robolectric.buildActivity(TestActivity::class.java).create().get()
            val viewUnderTest = WordView(activity)

            viewUnderTest.setWord(inputWordUI)
            val actualWordText = viewUnderTest.wordTv.text
            val actualPronunciationText = viewUnderTest.speakButton.text
            val actualSyllablesText = viewUnderTest.syllablesTv.text
            val actualFrequencyText = viewUnderTest.frequencyTv.text
            val actualDefinitionsCount = viewUnderTest.definitionsView.childCount

            assertEquals(expectedWordText, actualWordText)
            assertEquals(expectedPronunciationText, actualPronunciationText)
            assertEquals(expectedSyllablesText, actualSyllablesText)
            assertEquals(expectedFrequencyText, actualFrequencyText)
            assertEquals(expectedDefinitionsCount, actualDefinitionsCount)
        }

        companion object {
            @JvmStatic
            @ParameterizedRobolectricTestRunner.Parameters
            fun getParams(): Collection<Array<out Any?>> {
                return listOf(
                    arrayOf(
                        WordUI(
                            "answer",
                            "'ænsər",
                            "an-swer",
                            FrequencyUI.FREQUENT,
                            linkedMapOf(
                                PartOfSpeechUI.NOUN
                                to listOf(
                                    DefinitionUI(
                                        0,
                                        "answer",
                                        "the speech act of replying to a question",
                                        PartOfSpeechUI.NOUN,
                                        "their answer was to sue me",
                                        "response",
                                        "question, issue"
                                    ),
                                    DefinitionUI(
                                        0,
                                        "answer",
                                        "a nonverbal reaction",
                                        PartOfSpeechUI.NOUN,
                                        "his answer to any problem was to get drunk",
                                        "response, reaction",
                                        null
                                    )
                                ),
                                PartOfSpeechUI.VERB
                                to listOf(
                                    DefinitionUI(
                                        0,
                                        "answer",
                                        "understand the meaning of",
                                        PartOfSpeechUI.VERB,
                                        "The question concerning the meaning of life cannot be answered",
                                        "resolve",
                                        null
                                    )
                                )
                            ),
                            null
                        ),
                        "answer",
                        "'ænsər",
                        "an-swer",
                        "Frequent",
                        5
                    ),

                    arrayOf(
                        WordUI(
                            "hello",
                            "həˈləʊ",
                            "hel-lo",
                            FrequencyUI.VERY_FREQUENT,
                            null,
                            Date()
                        ),
                        "hello",
                        "həˈləʊ",
                        "hel-lo",
                        "Very frequent",
                        0
                    )
                )
            }
        }
    }
}