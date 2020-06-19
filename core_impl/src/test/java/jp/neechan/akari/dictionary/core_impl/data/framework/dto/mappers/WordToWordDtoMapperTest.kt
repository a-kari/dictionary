package jp.neechan.akari.dictionary.core_impl.data.framework.dto.mappers

import jp.neechan.akari.dictionary.core_api.domain.entities.Definition
import jp.neechan.akari.dictionary.core_api.domain.entities.Frequency
import jp.neechan.akari.dictionary.core_api.domain.entities.Word
import jp.neechan.akari.dictionary.core_api.domain.entities.mappers.ModelMapper
import jp.neechan.akari.dictionary.core_impl.data.framework.dto.DefinitionDto
import jp.neechan.akari.dictionary.core_impl.data.framework.dto.FrequencyDto
import jp.neechan.akari.dictionary.core_impl.data.framework.dto.WordDto
import jp.neechan.akari.dictionary.test_utils.mockito.MockitoUtils.anyNonNull
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoRule
import java.util.Date

@RunWith(Parameterized::class)
internal class WordToWordDtoMapperTest(private val wordDto: WordDto,
                                       private val word: Word) {

    @get:Rule
    val mockitoRule: MockitoRule = MockitoJUnit.rule()

    @Mock private lateinit var mockFrequencyMapper: ModelMapper<Frequency, FrequencyDto>
    @Mock private lateinit var mockDefinitionMapper: ModelMapper<Definition, DefinitionDto>

    private lateinit var mapperUnderTest: WordToWordDtoMapper

    @Before
    fun setup() {
        mapperUnderTest = WordToWordDtoMapper(mockFrequencyMapper, mockDefinitionMapper)
    }

    @Test
    fun `test mapToInternalLayer()`() {
        val inputWordDto = wordDto
        val expectedWord = word
        `when`(mockFrequencyMapper.mapToInternalLayer(anyNonNull())).thenReturn(Frequency.UNKNOWN)

        val actualWord = mapperUnderTest.mapToInternalLayer(inputWordDto)

        assertEquals(expectedWord, actualWord)
        verify(mockFrequencyMapper).mapToInternalLayer(inputWordDto.frequency)
        verify(mockDefinitionMapper, times(inputWordDto.definitions?.size ?: 0)).mapToInternalLayer(anyNonNull())
    }

    @Test
    fun `test mapToExternalLayer()`() {
        val inputWord = word
        val expectedWordDto = wordDto
        `when`(mockFrequencyMapper.mapToExternalLayer(anyNonNull())).thenReturn(FrequencyDto.UNKNOWN)

        val actualWordDto = mapperUnderTest.mapToExternalLayer(inputWord)

        assertEquals(expectedWordDto, actualWordDto)
        verify(mockFrequencyMapper).mapToExternalLayer(inputWord.frequency)
        verify(mockDefinitionMapper, times(inputWord.definitions?.size ?: 0)).mapToExternalLayer(anyNonNull())
    }

    companion object {
        @JvmStatic
        @Parameterized.Parameters
        fun getParams(): Collection<Array<Any>> {
            return listOf(
                arrayOf(
                    WordDto(
                        "hello",
                        null,
                        null,
                        FrequencyDto.UNKNOWN,
                        null,
                        null
                    ),
                    Word(
                        "hello",
                        null,
                        null,
                        Frequency.UNKNOWN,
                        null,
                        null
                    )
                ),

                arrayOf(
                    WordDto(
                        "hello",
                        "həˈləʊ",
                        listOf("hel", "lo"),
                        FrequencyDto.UNKNOWN,
                        null,
                        Date(1591850938247L)
                    ),
                    Word(
                        "hello",
                        "həˈləʊ",
                        listOf("hel", "lo"),
                        Frequency.UNKNOWN,
                        null,
                        Date(1591850938247L)
                    )
                )
            )
        }
    }
}