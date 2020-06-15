package jp.neechan.akari.dictionary.core_impl.data.interface_adapters

import jp.neechan.akari.dictionary.core_api.domain.entities.Result
import jp.neechan.akari.dictionary.core_api.domain.entities.Word
import jp.neechan.akari.dictionary.test_utils.mockito.MockitoUtils.anyNonNull
import jp.neechan.akari.dictionary.test_utils.mockito.MockitoUtils.anySuspendFunction
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.Mockito.verifyZeroInteractions
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoRule

class WordsRepositoryImplTest {

    @get:Rule
    val mockitoRule: MockitoRule = MockitoJUnit.rule()

    @Mock private lateinit var mockLocalSource: WordsLocalSource
    @Mock private lateinit var mockRemoteSource: WordsRemoteSource
    @Mock private lateinit var mockRemoteResultWrapper: ResultWrapper
    @Mock private lateinit var mockWord: Word

    private lateinit var repositoryUnderTest: WordsRepositoryImpl

    @Before
    fun setup() {
        repositoryUnderTest = WordsRepositoryImpl(mockLocalSource, mockRemoteSource, mockRemoteResultWrapper)
    }

    @Test
    fun `loadWord() should fetch a word from the local source`() = runBlockingTest {
        val inputWordId = "hello"
        val expectedWord = mockWord
        val expectedReturn = Result.Success(expectedWord)
        `when`(mockLocalSource.loadWord(anyNonNull())).thenReturn(expectedWord)

        val actualReturn = repositoryUnderTest.loadWord(inputWordId)

        assertEquals(expectedReturn, actualReturn)
        verify(mockLocalSource).loadWord(inputWordId)
        verifyZeroInteractions(mockRemoteSource)
        verifyZeroInteractions(mockRemoteResultWrapper)
    }

    @Test
    fun `loadWord() should try to fetch a word from the local source, then from remote`() = runBlockingTest {
        val inputWordId = "hello"
        val expectedReturn = Result.Success(mockWord)
        `when`(mockRemoteResultWrapper.wrapWithResult(anySuspendFunction<Word>())).thenReturn(expectedReturn)

        val actualReturn = repositoryUnderTest.loadWord(inputWordId)

        assertEquals(expectedReturn, actualReturn)
        verify(mockLocalSource).loadWord(inputWordId)
        verify(mockRemoteResultWrapper).wrapWithResult(anySuspendFunction<Word>())
    }

    @Test
    fun `save() should call localSource's save()`() = runBlockingTest {
        val expectedArgument = mockWord

        repositoryUnderTest.saveWord(expectedArgument)

        verify(mockLocalSource).saveWord(expectedArgument)
    }

    @Test
    fun `setSavedWordsRecentlyUpdated() should send a value to the channel`() = runBlockingTest {
        val expectedArgument = true

        repositoryUnderTest.setSavedWordsRecentlyUpdated(expectedArgument)

        repositoryUnderTest.savedWordsRecentlyUpdated.consumeEach { actualArgument ->
            assertEquals(expectedArgument, actualArgument)
            repositoryUnderTest.savedWordsRecentlyUpdated.close()
        }
    }
}
