package jp.neechan.akari.dictionary.core_impl.data.framework.db.dao

import jp.neechan.akari.dictionary.core_api.domain.entities.Word
import jp.neechan.akari.dictionary.core_api.domain.entities.mappers.ModelMapper
import jp.neechan.akari.dictionary.core_impl.data.framework.dto.DefinitionDto
import jp.neechan.akari.dictionary.core_impl.data.framework.dto.WordDto
import jp.neechan.akari.dictionary.core_impl.data.framework.dto.WordWithDefinitionsDto
import jp.neechan.akari.dictionary.test_utils.mockito.MockitoUtils.anyNonNull
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoRule

class WordsLocalSourceImplTest {

    @get:Rule
    val mockitoRule: MockitoRule = MockitoJUnit.rule()

    @Mock private lateinit var mockWordsDao: WordsDao
    @Mock private lateinit var mockWordMapper: ModelMapper<Word, WordDto>
    @Mock private lateinit var mockWord: Word
    @Mock private lateinit var mockWordDto: WordDto
    @Mock private lateinit var mockDefinitionsDto: List<DefinitionDto>

    private lateinit var mockWordWithDefinitionDto: WordWithDefinitionsDto
    private lateinit var sourceUnderTest: WordsLocalSourceImpl

    @Before
    fun setup() {
        mockWordWithDefinitionDto = WordWithDefinitionsDto(mockWordDto, mockDefinitionsDto)
        sourceUnderTest = WordsLocalSourceImpl(mockWordsDao, mockWordMapper)
    }

    @Test
    fun `loadWord() should return null when there is no word in db`() = runBlockingTest {
        val inputWordId = "hello"
        val expectedReturn: WordDto? = null

        val actualReturn = sourceUnderTest.loadWord(inputWordId)

        verify(mockWordsDao).getWordWithDefinitions(inputWordId)
        assertEquals(expectedReturn, actualReturn)
    }

    @Test
    fun `loadWord() should return a word from db`() = runBlockingTest {
        val inputWordId = "hello"
        val expectedWordWithDefinitions = mockWordWithDefinitionDto
        val expectedReturn = mockWord
        `when`(mockWordsDao.getWordWithDefinitions(anyString())).thenReturn(expectedWordWithDefinitions)
        `when`(mockWordMapper.mapToInternalLayer(anyNonNull())).thenReturn(expectedReturn)

        val actualReturn = sourceUnderTest.loadWord(inputWordId)

        verify(mockWordsDao).getWordWithDefinitions(inputWordId)
        verify(mockWordMapper).mapToInternalLayer(expectedWordWithDefinitions.word)
        assertEquals(expectedReturn, actualReturn)
    }

    @Test
    fun `saveWord() should save a word to db with definitions`() = runBlockingTest {
        val inputWord = mockWord
        val expectedWordDto = mockWordDto
        `when`(mockWordMapper.mapToExternalLayer(anyNonNull())).thenReturn(expectedWordDto)

        sourceUnderTest.saveWord(inputWord)

        verify(mockWordMapper).mapToExternalLayer(inputWord)
        verify(expectedWordDto).saveDate = anyNonNull()
        verify(mockWordsDao).saveWord(expectedWordDto)
        verify(mockWordsDao).saveDefinitions(expectedWordDto.definitions!!)
    }
}