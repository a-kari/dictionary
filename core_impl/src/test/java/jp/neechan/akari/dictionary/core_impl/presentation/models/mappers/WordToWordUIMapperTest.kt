package jp.neechan.akari.dictionary.core_impl.presentation.models.mappers

import jp.neechan.akari.dictionary.core_api.domain.entities.Definition
import jp.neechan.akari.dictionary.core_api.domain.entities.Frequency
import jp.neechan.akari.dictionary.core_api.domain.entities.PartOfSpeech
import jp.neechan.akari.dictionary.core_api.domain.entities.Word
import jp.neechan.akari.dictionary.core_api.domain.entities.mappers.ModelMapper
import jp.neechan.akari.dictionary.core_api.presentation.models.DefinitionUI
import jp.neechan.akari.dictionary.core_api.presentation.models.FrequencyUI
import jp.neechan.akari.dictionary.core_api.presentation.models.PartOfSpeechUI
import jp.neechan.akari.dictionary.core_api.presentation.models.WordUI
import jp.neechan.akari.dictionary.test_utils.mockito.MockitoUtils.anyNonNull
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoRule
import java.util.Date

@RunWith(Parameterized::class)
class WordToWordUIMapperTest(private val word: Word, private val wordUI: WordUI) {

    @get:Rule
    val mockitoRule: MockitoRule = MockitoJUnit.rule()

    @Mock private lateinit var mockFrequencyMapper: ModelMapper<Frequency, FrequencyUI>
    @Mock private lateinit var mockPartOfSpeechMapper: ModelMapper<PartOfSpeech, PartOfSpeechUI>
    @Mock private lateinit var mockDefinitionMapper: ModelMapper<Definition, DefinitionUI>

    private lateinit var mapperUnderTest: WordToWordUIMapper

    @Before
    fun setup() {
        mapperUnderTest = WordToWordUIMapper(mockFrequencyMapper, mockPartOfSpeechMapper, mockDefinitionMapper)
    }

    @Test
    fun `test mapToInternalLayer()`() {
        val inputWordUI = wordUI
        val expectedWord = word
        `when`(mockFrequencyMapper.mapToInternalLayer(anyNonNull())).thenReturn(Frequency.UNKNOWN)

        val actualWord = mapperUnderTest.mapToInternalLayer(inputWordUI)

        assertEquals(expectedWord, actualWord)
        verify(mockFrequencyMapper).mapToInternalLayer(inputWordUI.frequency)
    }

    @Test
    fun `test mapToExternalLayer()`() {
        val inputWord = word
        val expectedWordUI = wordUI
        `when`(mockFrequencyMapper.mapToExternalLayer(anyNonNull())).thenReturn(FrequencyUI.UNKNOWN)

        val actualWordUI = mapperUnderTest.mapToExternalLayer(inputWord)

        assertEquals(expectedWordUI, actualWordUI)
        verify(mockFrequencyMapper).mapToExternalLayer(inputWord.frequency)
    }

    companion object {
        @JvmStatic
        @Parameterized.Parameters
        fun getParams(): Collection<Array<Any>> {
            return listOf(
                arrayOf(
                    Word(
                        "hello",
                        "həˈləʊ",
                        listOf("hel", "lo"),
                        Frequency.UNKNOWN,
                        null,
                        Date(1591850938247L)
                    ),
                    WordUI (
                        "hello",
                        "həˈləʊ",
                        "hel-lo",
                        FrequencyUI.UNKNOWN,
                        null,
                        Date(1591850938247L)
                    )
                ),

                arrayOf(
                    Word(
                        "hello",
                        "həˈləʊ",
                        null,
                        Frequency.UNKNOWN,
                        null,
                        null
                    ),
                    WordUI (
                        "hello",
                        "həˈləʊ",
                        null,
                        FrequencyUI.UNKNOWN,
                        null,
                        null
                    )
                )
            )
        }
    }
}