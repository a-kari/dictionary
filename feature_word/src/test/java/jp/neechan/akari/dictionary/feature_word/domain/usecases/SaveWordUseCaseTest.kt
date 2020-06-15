package jp.neechan.akari.dictionary.feature_word.domain.usecases

import jp.neechan.akari.dictionary.core_api.domain.entities.Word
import jp.neechan.akari.dictionary.core_api.domain.usecases.WordsRepository
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoRule

class SaveWordUseCaseTest {

    @get:Rule
    val mockitoRule: MockitoRule = MockitoJUnit.rule()

    @Mock private lateinit var mockWordsRepository: WordsRepository
    @Mock private lateinit var mockWord: Word

    private lateinit var useCaseUnderTest: SaveWordUseCase

    @Before
    fun setup() {
        useCaseUnderTest = SaveWordUseCase(mockWordsRepository)
    }

    @Test
    fun `should call wordsRepository's saveWord() and setSavedWordsRecentlyUpdated(true)`() = runBlockingTest {
        val expectedWord = mockWord
        val expectedRecentlyUpdated = true

        useCaseUnderTest.invoke(expectedWord)

        verify(mockWordsRepository).saveWord(expectedWord)
        verify(mockWordsRepository).setSavedWordsRecentlyUpdated(expectedRecentlyUpdated)
    }
}