package jp.neechan.akari.dictionary.domain_words.domain

import jp.neechan.akari.dictionary.core_api.domain.entities.Result
import jp.neechan.akari.dictionary.core_api.domain.entities.Word
import jp.neechan.akari.dictionary.core_api.domain.usecases.WordsRepository
import jp.neechan.akari.dictionary.test_utils.mockito.MockitoUtils.anyNonNull
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoRule

@RunWith(Parameterized::class)
class LoadWordUseCaseTest(private val expectedReturn: Result<Word>) {

    @get:Rule
    val mockitoRule: MockitoRule = MockitoJUnit.rule()

    @Mock private lateinit var mockWordsRepository: WordsRepository

    private lateinit var useCaseUnderTest: LoadWordUseCase

    @Before
    fun setup() {
        useCaseUnderTest = LoadWordUseCase(mockWordsRepository)
    }

    @Test
    fun `should return the same value as wordsRepository's loadWord()`() = runBlockingTest {
        val inputWord = "hello"
        `when`(mockWordsRepository.loadWord(anyNonNull())).thenReturn(expectedReturn)

        val actualReturn = useCaseUnderTest.invoke(inputWord)

        assertEquals(expectedReturn, actualReturn)
        verify(mockWordsRepository).loadWord(inputWord)
    }

    companion object {
        @JvmStatic
        @Parameterized.Parameters
        fun getParams(): List<Array<out Result<Word>>> {
            return listOf(
                arrayOf(Result.Success(mock(Word::class.java))),
                arrayOf(Result.Error(RuntimeException("Something went wrong"))),
                arrayOf(Result.ConnectionError),
                arrayOf(Result.NotFoundError),
                arrayOf(Result.Loading)
            )
        }
    }
}