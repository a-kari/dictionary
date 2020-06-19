package jp.neechan.akari.dictionary.core_impl.data.framework.dto.mappers

import jp.neechan.akari.dictionary.core_api.domain.entities.PartOfSpeech
import jp.neechan.akari.dictionary.core_impl.data.framework.dto.PartOfSpeechDto
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized

@RunWith(Parameterized::class)
internal class PartOfSpeechToPartOfSpeechDtoMapperTest(private val partOfSpeechDto: PartOfSpeechDto,
                                                       private val partOfSpeech: PartOfSpeech) {

    private val mapperUnderTest = PartOfSpeechToPartOfSpeechDtoMapper()

    @Test
    fun `test mapToInternalLayer()`() {
        val inputPartOfSpeechDto = partOfSpeechDto
        val expectedPartOfSpeech = partOfSpeech

        val actualPartOfSpeech = mapperUnderTest.mapToInternalLayer(inputPartOfSpeechDto)

        assertEquals(expectedPartOfSpeech, actualPartOfSpeech)
    }

    @Test
    fun `test mapToExternalLayer()`() {
        val inputPartOfSpeech = partOfSpeech
        val expectedPartOfSpeechDto = partOfSpeechDto

        val actualPartOfSpeechDto = mapperUnderTest.mapToExternalLayer(inputPartOfSpeech)

        assertEquals(expectedPartOfSpeechDto, actualPartOfSpeechDto)
    }

    companion object {
        @JvmStatic
        @Parameterized.Parameters
        fun getParams(): Collection<Array<Enum<*>>> {
            return listOf(
                arrayOf(PartOfSpeechDto.NOUN,        PartOfSpeech.NOUN),
                arrayOf(PartOfSpeechDto.PRONOUN,     PartOfSpeech.PRONOUN),
                arrayOf(PartOfSpeechDto.ADJECTIVE,   PartOfSpeech.ADJECTIVE),
                arrayOf(PartOfSpeechDto.VERB,        PartOfSpeech.VERB),
                arrayOf(PartOfSpeechDto.ADVERB,      PartOfSpeech.ADVERB),
                arrayOf(PartOfSpeechDto.PREPOSITION, PartOfSpeech.PREPOSITION),
                arrayOf(PartOfSpeechDto.CONJUNCTION, PartOfSpeech.CONJUNCTION),
                arrayOf(PartOfSpeechDto.UNKNOWN,     PartOfSpeech.UNKNOWN),
                arrayOf(PartOfSpeechDto.ALL,         PartOfSpeech.ALL)
            )
        }
    }
}