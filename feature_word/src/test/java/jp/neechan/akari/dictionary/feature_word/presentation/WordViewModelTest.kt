package jp.neechan.akari.dictionary.feature_word.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import jp.neechan.akari.dictionary.core_api.domain.entities.Frequency
import jp.neechan.akari.dictionary.core_api.domain.entities.Result
import jp.neechan.akari.dictionary.core_api.domain.entities.Word
import jp.neechan.akari.dictionary.core_api.domain.entities.mappers.ModelMapper
import jp.neechan.akari.dictionary.core_api.presentation.models.FrequencyUI
import jp.neechan.akari.dictionary.core_api.presentation.models.UIState
import jp.neechan.akari.dictionary.core_api.presentation.models.WordUI
import jp.neechan.akari.dictionary.domain_tts.domain.SpeakUseCase
import jp.neechan.akari.dictionary.domain_tts.domain.StopSpeakingUseCase
import jp.neechan.akari.dictionary.domain_words.domain.LoadWordUseCase
import jp.neechan.akari.dictionary.feature_word.domain.usecases.SaveWordUseCase
import jp.neechan.akari.dictionary.test_utils.junit.CoroutinesRule
import jp.neechan.akari.dictionary.test_utils.mockito.MockitoUtils.anyNonNull
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoRule

class WordViewModelTest {

    @get:Rule
    val mockitoRule: MockitoRule = MockitoJUnit.rule()

    // Run LiveData methods synchronously.
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    // Switch Dispatchers.Main to TestCoroutineDispatcher.
    @get:Rule
    val coroutinesRule = CoroutinesRule()

    @Mock private lateinit var mockLoadWordUseCase: LoadWordUseCase
    @Mock private lateinit var mockSaveWordUseCase: SaveWordUseCase
    @Mock private lateinit var mockSpeakUseCase: SpeakUseCase
    @Mock private lateinit var mockStopSpeakingUseCase: StopSpeakingUseCase
    @Mock private lateinit var mockResultMapper: ModelMapper<Result<Word>, UIState<WordUI>>
    @Mock private lateinit var mockWordMapper: ModelMapper<Word, WordUI>

    private lateinit var viewModelUnderTest: WordViewModel

    @Before
    fun setup() {
        viewModelUnderTest = WordViewModel(
            mockLoadWordUseCase,
            mockSaveWordUseCase,
            mockResultMapper,
            mockWordMapper,
            mockSpeakUseCase,
            mockStopSpeakingUseCase
        )
    }

    @Test
    fun `wordLiveData should be populated on observing`() = runBlockingTest {
        val inputWordId = "hello"
        val wordResult = Result.Success(Word(
            "hello",
            "həˈləʊ",
            listOf("hel", "lo"),
            Frequency.FREQUENT,
            null,
            null
        ))
        val expectedUIState1 = UIState.ShowLoading
        val expectedUIState2 = UIState.ShowContent(WordUI(
            "hello",
            "həˈləʊ",
            "hel-lo",
            FrequencyUI.FREQUENT,
            null,
            null,
            false
        ))
        `when`(mockLoadWordUseCase.invoke(anyNonNull())).thenReturn(wordResult)
        `when`(mockResultMapper.mapToExternalLayer(anyNonNull())).thenReturn(expectedUIState2)
        viewModelUnderTest.wordId = inputWordId

        // Check that the ViewModel posts ShowLoading and then ShowContent to the LiveData.
        viewModelUnderTest.wordLiveData.observeForever(object : Observer<UIState<WordUI>> {
            private var receivedValues = 0

            override fun onChanged(actualUIState: UIState<WordUI>?) {
                if (receivedValues == 0) {
                    assertEquals(expectedUIState1, actualUIState)
                    ++receivedValues

                } else {
                    viewModelUnderTest.wordLiveData.removeObserver(this)
                    assertEquals(expectedUIState2, actualUIState)
                }
            }
        })
    }

    @Test
    fun `saveWord() should call saveWordUseCase`() = runBlocking {
        val inputWordUI = WordUI(
            "hello",
            "həˈləʊ",
            "hel-lo",
            FrequencyUI.FREQUENT,
            null,
            null,
            false
        )
        val expectedArgument = Word(
            "hello",
            "həˈləʊ",
            listOf("hel", "lo"),
            Frequency.FREQUENT,
            null,
            null
        )
        `when`(mockWordMapper.mapToInternalLayer(anyNonNull())).thenReturn(expectedArgument)

        viewModelUnderTest.saveWord(inputWordUI).join()

        verify(mockWordMapper).mapToInternalLayer(inputWordUI)
        verify(mockSaveWordUseCase).invoke(expectedArgument)
    }

    @Test
    fun `speak() should call speakUseCase`() = runBlockingTest {
        val inputWordId = "hello"
        viewModelUnderTest.wordId = inputWordId

        viewModelUnderTest.speak()

        verify(mockSpeakUseCase).invoke(inputWordId)
    }
}