package jp.neechan.akari.dictionary.core_impl.data.framework.network

import jp.neechan.akari.dictionary.core_api.domain.entities.FilterParams
import jp.neechan.akari.dictionary.core_api.domain.entities.Word
import jp.neechan.akari.dictionary.core_api.domain.entities.mappers.ModelMapper
import jp.neechan.akari.dictionary.core_impl.data.framework.dto.FilterParamsDto
import jp.neechan.akari.dictionary.core_impl.data.framework.dto.WordDto
import jp.neechan.akari.dictionary.test_utils.mockito.MockitoUtils.anyNonNull
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.anyString
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoRule

class WordsRemoteSourceImplTest {

    @get:Rule
    val mockitoRule: MockitoRule = MockitoJUnit.rule()

    @Mock private lateinit var mockWordsApi: WordsApi
    @Mock private lateinit var mockWordMapper: ModelMapper<Word, WordDto>
    @Mock private lateinit var mockParamsMapper: ModelMapper<FilterParams, FilterParamsDto>
    @Mock private lateinit var mockWordDto: WordDto
    @Mock private lateinit var mockWord: Word

    private lateinit var sourceUnderTest: WordsRemoteSourceImpl

    @Before
    fun setup() {
        sourceUnderTest = WordsRemoteSourceImpl(mockWordsApi, mockWordMapper, mockParamsMapper)
    }

    @Test
    fun `loadWord() should return a mapped word from api`() = runBlockingTest {
        val input = "hello"
        val expectedWordDto = mockWordDto
        val expectedWord = mockWord
        `when`(mockWordsApi.loadWord(anyString())).thenReturn(expectedWordDto)
        `when`(mockWordMapper.mapToInternalLayer(anyNonNull())).thenReturn(expectedWord)

        val actualWord = sourceUnderTest.loadWord(input)

        verify(mockWordsApi).loadWord(input)
        verify(mockWordMapper).mapToInternalLayer(expectedWordDto)
        assertEquals(expectedWord, actualWord)
    }
}