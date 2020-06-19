package jp.neechan.akari.dictionary.core_impl.data.framework.dto.mappers

import jp.neechan.akari.dictionary.core_api.domain.entities.Frequency
import jp.neechan.akari.dictionary.core_impl.data.framework.dto.FrequencyDto
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized

@RunWith(Parameterized::class)
internal class FrequencyToFrequencyDtoMapperTest(private val frequencyDto: FrequencyDto,
                                                 private val frequency: Frequency) {

    private val mapperUnderTest = FrequencyToFrequencyDtoMapper()

    @Test
    fun `test mapToInternalLayer()`() {
        val inputFrequencyDto = frequencyDto
        val expectedFrequency = frequency

        val actualFrequency = mapperUnderTest.mapToInternalLayer(inputFrequencyDto)

        assertEquals(expectedFrequency, actualFrequency)
    }

    @Test
    fun `test mapToExternalLayer()`() {
        val inputFrequency = frequency
        val expectedFrequencyDto = frequencyDto

        val actualFrequencyDto = mapperUnderTest.mapToExternalLayer(inputFrequency)

        assertEquals(expectedFrequencyDto, actualFrequencyDto)
    }

    companion object {
        @JvmStatic
        @Parameterized.Parameters
        fun getParams(): Collection<Array<Enum<*>>> {
            return listOf(
                arrayOf(FrequencyDto.VERY_RARE,     Frequency.VERY_RARE),
                arrayOf(FrequencyDto.RARE,          Frequency.RARE),
                arrayOf(FrequencyDto.NORMAL,        Frequency.NORMAL),
                arrayOf(FrequencyDto.FREQUENT,      Frequency.FREQUENT),
                arrayOf(FrequencyDto.VERY_FREQUENT, Frequency.VERY_FREQUENT),
                arrayOf(FrequencyDto.UNKNOWN,       Frequency.UNKNOWN)
            )
        }
    }
}