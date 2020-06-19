package jp.neechan.akari.dictionary.core_impl.data.framework.dto.mappers

import jp.neechan.akari.dictionary.core_api.domain.entities.Definition
import jp.neechan.akari.dictionary.core_api.domain.entities.PartOfSpeech
import jp.neechan.akari.dictionary.core_api.domain.entities.mappers.ModelMapper
import jp.neechan.akari.dictionary.core_impl.data.framework.dto.DefinitionDto
import jp.neechan.akari.dictionary.core_impl.data.framework.dto.PartOfSpeechDto
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
internal class DefinitionToDefinitionDtoMapperTest(private val definitionDto: DefinitionDto,
                                                   private val definition: Definition) {

    @get:Rule
    val mockitoRule: MockitoRule = MockitoJUnit.rule()

    @Mock
    private lateinit var mockPartOfSpeechMapper: ModelMapper<PartOfSpeech, PartOfSpeechDto>

    private lateinit var mapperUnderTest: DefinitionToDefinitionDtoMapper

    @Before
    fun setup() {
        mapperUnderTest = DefinitionToDefinitionDtoMapper(mockPartOfSpeechMapper)
    }

    @Test
    fun `test mapToInternalLayer()`() {
        val inputDefinitionDto = definitionDto
        val expectedDefinition = definition
        `when`(mockPartOfSpeechMapper.mapToInternalLayer(anyNonNull())).thenReturn(PartOfSpeech.UNKNOWN)

        val actualDefinition = mapperUnderTest.mapToInternalLayer(inputDefinitionDto)

        assertEquals(expectedDefinition, actualDefinition)
        verify(mockPartOfSpeechMapper).mapToInternalLayer(inputDefinitionDto.partOfSpeech)
    }

    @Test
    fun `test mapToExternalLayer()`() {
        val inputDefinition = definition
        val expectedDefinitionDto = definitionDto
        `when`(mockPartOfSpeechMapper.mapToExternalLayer(anyNonNull())).thenReturn(PartOfSpeechDto.UNKNOWN)

        val actualDefinitionDto = mapperUnderTest.mapToExternalLayer(inputDefinition)

        assertEquals(expectedDefinitionDto, actualDefinitionDto)
        verify(mockPartOfSpeechMapper).mapToExternalLayer(inputDefinition.partOfSpeech)
    }

    companion object {
        @JvmStatic
        @Parameterized.Parameters
        fun getParams(): Collection<Array<Any>> {
            return listOf(
                arrayOf(
                    DefinitionDto(
                        0,
                        "hello",
                        "an expression of greeting",
                        PartOfSpeechDto.UNKNOWN,
                        listOf("every morning they exchanged polite hellos"),
                        listOf("hi", "how-do-you-do", "howdy", "hullo"),
                        null
                    ),
                    Definition(
                        0,
                        "hello",
                        "an expression of greeting",
                        PartOfSpeech.UNKNOWN,
                        listOf("every morning they exchanged polite hellos"),
                        listOf("hi", "how-do-you-do", "howdy", "hullo"),
                        null
                    )
                ),

                arrayOf(
                    DefinitionDto(
                        0,
                        "clean",
                        "make clean by removing dirt, filth, or unwanted substances from",
                        PartOfSpeechDto.UNKNOWN,
                        listOf("clean up before you see your grandparents", "clean your fingernails before dinner"),
                        listOf("make clean"),
                        listOf("dirty")
                    ),
                    Definition(
                        0,
                        "clean",
                        "make clean by removing dirt, filth, or unwanted substances from",
                        PartOfSpeech.UNKNOWN,
                        listOf("clean up before you see your grandparents", "clean your fingernails before dinner"),
                        listOf("make clean"),
                        listOf("dirty")
                    )
                )
            )
        }
    }
}