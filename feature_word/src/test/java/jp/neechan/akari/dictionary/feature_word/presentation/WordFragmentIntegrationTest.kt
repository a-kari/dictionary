package jp.neechan.akari.dictionary.feature_word.presentation

import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.Lifecycle
import jp.neechan.akari.dictionary.core_api.domain.entities.Definition
import jp.neechan.akari.dictionary.core_api.domain.entities.Frequency
import jp.neechan.akari.dictionary.core_api.domain.entities.PartOfSpeech
import jp.neechan.akari.dictionary.core_api.domain.entities.Word
import jp.neechan.akari.dictionary.test_utils.junit.CoroutinesRule
import jp.neechan.akari.dictionary.test_utils.robolectric.di.TestAppWithFacade
import kotlinx.android.synthetic.main.fragment_word.*
import kotlinx.android.synthetic.main.view_word.*
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.yield
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import java.net.HttpURLConnection
import java.util.concurrent.TimeUnit

@RunWith(RobolectricTestRunner::class)
@Config(application = TestAppWithFacade::class)
class WordFragmentIntegrationTest {

    // Delegate Dispatchers.Main to TestCoroutineDispatcher.
    @get:Rule
    val coroutinesRule = CoroutinesRule()

    private val mockWebServer = MockWebServer()

    @Before
    fun setup() {
        mockWebServer.start(8080)
    }

    @After
    fun teardown() {
        mockWebServer.shutdown()
    }

    @Test
    fun `should fetch a word from api and populate the view`() = runBlockingTest {
        val response = MockResponse().setResponseCode(HttpURLConnection.HTTP_OK)
                                     .setBody(SAMPLE_API_WORD_JSON)
                                     .setBodyDelay(0, TimeUnit.MILLISECONDS)
        mockWebServer.enqueue(response)

        // Launch WordFragment. It immediately makes an API call via WordViewModel.
        val arguments = Bundle().apply { putString("wordId", SAMPLE_WORD_ID) }
        val fragmentScenario = launchFragmentInContainer<WordFragment>(arguments).moveToState(Lifecycle.State.RESUMED)

        // Wait until WordFragment finishes the API call, and make some assertions.
        fragmentScenario.onFragment { fragmentUnderTest ->
            runBlocking {
                while (fragmentUnderTest.progressBar.visibility == VISIBLE) { yield() }
                assertViewCorrectlyPopulatedWith(SAMPLE_API_WORD, fragmentUnderTest)
            }
        }
    }

    private fun assertViewCorrectlyPopulatedWith(word: Word, view: WordFragment) {
        val expectedWordText = word.word
        val expectedPronunciationText = word.pronunciation
        val expectedSyllablesText = word.syllables?.joinToString("-")
        val expectedFrequencyText = word.frequency.name.toLowerCase().capitalize()
        val expectedSaveButtonVisibility = if (word.saveDate == null) VISIBLE else GONE
        val expectedDefinitionsCount = (word.definitions?.size ?: 0) + (word.definitions?.groupBy { it.partOfSpeech }?.keys?.size ?: 0)

        val actualWordText = view.wordTv.text
        val actualPronunciationText = view.speakButton.text
        val actualSyllablesText = view.syllablesTv.text
        val actualFrequencyText = view.frequencyTv.text
        val actualSaveButtonVisibility = view.addToDictionaryButton.visibility
        val actualDefinitionsCount = view.definitionsView.childCount

        assertEquals(expectedWordText, actualWordText)
        assertEquals(expectedPronunciationText, actualPronunciationText)
        assertEquals(expectedSyllablesText, actualSyllablesText)
        assertEquals(expectedFrequencyText, actualFrequencyText)
        assertEquals(expectedSaveButtonVisibility, actualSaveButtonVisibility)
        assertEquals(expectedDefinitionsCount, actualDefinitionsCount)
    }

    companion object {
        private const val SAMPLE_WORD_ID = "answer"

        private const val SAMPLE_API_WORD_JSON = "{\"word\":\"answer\",\"results\":[{\"definition\":\"the speech act of replying to a question\",\"partOfSpeech\":\"noun\",\"typeOf\":[\"reply\",\"response\"],\"hasTypes\":[\"urim and thummim\",\"refutation\",\"defence\",\"defense\"],\"examples\":[\"their answer was to sue me\"],\"synonyms\":[\"response\"],\"antonyms\":[\"question\",\"issue\"]},{\"definition\":\"understand the meaning of\",\"partOfSpeech\":\"verb\",\"synonyms\":[\"resolve\"],\"typeOf\":[\"work\",\"work out\",\"puzzle out\",\"lick\",\"figure out\",\"solve\"],\"derivation\":[\"answerable\"],\"examples\":[\"The question concerning the meaning of life cannot be answered\"]}],\"syllables\":{\"count\":2,\"list\":[\"an\",\"swer\"]},\"pronunciation\":{\"all\":\"'ænsər\"},\"frequency\":5.25}"

        private val SAMPLE_API_WORD = Word(
            "answer",
            "'ænsər",
            listOf("an", "swer"),
            Frequency.FREQUENT,
            listOf(
                Definition(
                    0,
                    "answer",
                    "the speech act of replying to a question",
                    PartOfSpeech.NOUN,
                    listOf("their answer was to sue me"),
                    listOf("response"),
                    listOf("question", "issue")
                ),
                Definition(
                    0,
                    "answer",
                    "understand the meaning of",
                    PartOfSpeech.VERB,
                    listOf("The question concerning the meaning of life cannot be answered"),
                    null,
                    null
                )
            ),
            null
        )
    }
}