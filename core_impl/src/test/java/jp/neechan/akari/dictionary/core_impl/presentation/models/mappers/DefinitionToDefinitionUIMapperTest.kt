package jp.neechan.akari.dictionary.core_impl.presentation.models.mappers

import jp.neechan.akari.dictionary.core_api.domain.entities.Definition
import jp.neechan.akari.dictionary.core_api.domain.entities.PartOfSpeech
import jp.neechan.akari.dictionary.core_api.domain.entities.mappers.ModelMapper
import jp.neechan.akari.dictionary.core_api.presentation.models.DefinitionUI
import jp.neechan.akari.dictionary.core_api.presentation.models.PartOfSpeechUI
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

@RunWith(Parameterized::class)
class DefinitionToDefinitionUIMapperTest(private val definition: Definition,
                                         private val definitionUI: DefinitionUI) {

    @get:Rule
    val mockitoRule: MockitoRule = MockitoJUnit.rule()

    @Mock
    private lateinit var mockPartOfSpeechMapper: ModelMapper<PartOfSpeech, PartOfSpeechUI>

    private lateinit var mapperUnderTest: DefinitionToDefinitionUIMapper

    @Before
    fun setup() {
        mapperUnderTest = DefinitionToDefinitionUIMapper(mockPartOfSpeechMapper)
    }

    @Test
    fun `test mapToInternalLayer()`() {
        val inputDefinitionUI = definitionUI
        val expectedDefinition = definition
        `when`(mockPartOfSpeechMapper.mapToInternalLayer(anyNonNull())).thenReturn(PartOfSpeech.UNKNOWN)

        val actualDefinition = mapperUnderTest.mapToInternalLayer(inputDefinitionUI)

        assertEquals(expectedDefinition, actualDefinition)
        verify(mockPartOfSpeechMapper).mapToInternalLayer(inputDefinitionUI.partOfSpeech)
    }

    @Test
    fun `test mapToExternalLayer()`() {
        val inputDefinition = definition
        val expectedDefinitionUI = definitionUI
        `when`(mockPartOfSpeechMapper.mapToExternalLayer(anyNonNull())).thenReturn(PartOfSpeechUI.UNKNOWN)

        val actualDefinitionUI = mapperUnderTest.mapToExternalLayer(inputDefinition)

        assertEquals(expectedDefinitionUI, actualDefinitionUI)
        verify(mockPartOfSpeechMapper).mapToExternalLayer(inputDefinition.partOfSpeech)
    }

    companion object {
        @JvmStatic
        @Parameterized.Parameters
        fun getParams(): Collection<Array<Any>> {
            return listOf(
                arrayOf(
                    Definition(
                        0,
                        "hello",
                        "an expression of greeting",
                        PartOfSpeech.UNKNOWN,
                        listOf("every morning they exchanged polite hellos", "hello there"),
                        listOf("hi", "how-do-you-do", "howdy", "hullo"),
                        listOf("goodbye", "bye")
                    ),
                    DefinitionUI(
                        0,
                        "hello",
                        "an expression of greeting",
                        PartOfSpeechUI.UNKNOWN,
                        "\"every morning they exchanged polite hellos\"\n\"hello there\"",
                        "hi, how-do-you-do, howdy, hullo",
                        "goodbye, bye"
                    )
                ),

                arrayOf(
                    Definition(
                        0,
                        "hello",
                        "an expression of greeting",
                        PartOfSpeech.UNKNOWN,
                        null,
                        null,
                        null
                    ),
                    DefinitionUI(
                        0,
                        "hello",
                        "an expression of greeting",
                        PartOfSpeechUI.UNKNOWN,
                        null,
                        null,
                        null
                    )
                )
            )
        }
    }
}