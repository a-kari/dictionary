package jp.neechan.akari.dictionary.feature_word.presentation

import jp.neechan.akari.dictionary.core_api.domain.entities.Result
import jp.neechan.akari.dictionary.core_api.domain.entities.Word
import jp.neechan.akari.dictionary.core_api.domain.entities.mappers.ModelMapper
import jp.neechan.akari.dictionary.core_api.presentation.models.UIState
import jp.neechan.akari.dictionary.core_api.presentation.models.WordUI
import jp.neechan.akari.dictionary.domain_tts.domain.SpeakUseCase
import jp.neechan.akari.dictionary.domain_tts.domain.StopSpeakingUseCase
import jp.neechan.akari.dictionary.domain_words.domain.LoadWordUseCase
import jp.neechan.akari.dictionary.feature_word.domain.usecases.SaveWordUseCase
import jp.neechan.akari.dictionary.test_utils.NonexistentViewModel
import org.hamcrest.CoreMatchers.instanceOf
import org.junit.Assert.assertThat
import org.junit.Assert.assertTrue
import org.junit.Assert.fail
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoRule

class WordViewModelFactoryTest {

    @get:Rule
    val mockitoRule: MockitoRule = MockitoJUnit.rule()

    @Mock private lateinit var mockLoadWordUseCase: LoadWordUseCase
    @Mock private lateinit var mockSaveWordUseCase: SaveWordUseCase
    @Mock private lateinit var mockResultMapper: ModelMapper<Result<Word>, UIState<WordUI>>
    @Mock private lateinit var mockWordMapper: ModelMapper<Word, WordUI>
    @Mock private lateinit var mockSpeakUseCase: SpeakUseCase
    @Mock private lateinit var mockStopSpeakingUseCase: StopSpeakingUseCase

    private lateinit var factoryUnderTest: WordViewModelFactory

    @Before
    fun setup() {
        factoryUnderTest = WordViewModelFactory(
            mockLoadWordUseCase,
            mockSaveWordUseCase,
            mockResultMapper,
            mockWordMapper,
            mockSpeakUseCase,
            mockStopSpeakingUseCase
        )
    }

    @Test
    fun `should instantiate a WordViewModel`() {
        val expectedViewModelClass = WordViewModel::class.java

        val actualViewModel = factoryUnderTest.create(expectedViewModelClass)

        assertThat(actualViewModel, instanceOf(expectedViewModelClass))
    }

    @Test
    fun `should fail due to "cannot instantiate" exception`() {
        try {
            factoryUnderTest.create(NonexistentViewModel::class.java)
            fail("A \"Cannot instantiate NonexistentViewModel\" exception should be thrown, but it wasn't")

        } catch (t: Throwable) {
            assertThat(t, instanceOf(IllegalArgumentException::class.java))
            assertTrue(t.message!!.contains("Cannot instantiate", true))
        }
    }
}