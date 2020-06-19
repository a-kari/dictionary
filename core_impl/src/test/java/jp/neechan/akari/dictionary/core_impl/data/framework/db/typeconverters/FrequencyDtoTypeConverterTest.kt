package jp.neechan.akari.dictionary.core_impl.data.framework.db.typeconverters

import jp.neechan.akari.dictionary.core_impl.data.framework.dto.FrequencyDto
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized

@RunWith(Parameterized::class)
internal class FrequencyDtoTypeConverterTest(private val inputFrequency: FrequencyDto) {

    private val converterUnderTest = FrequencyDtoTypeConverter()

    // The test calls two methods of the unit (save() & restore()).
    // But it doesn't matter in what form the unit saves the frequency -
    // actually, we just need to check that a restored frequency equals to original.
    @Test
    fun `restored frequency should be equal to input`() {
        val expectedFrequency = inputFrequency

        val actualFrequency = converterUnderTest.restore(converterUnderTest.save(inputFrequency))

        assertEquals(expectedFrequency, actualFrequency)
    }

    companion object {
        @JvmStatic
        @Parameterized.Parameters
        fun getParams(): List<FrequencyDto> {
            return listOf(
                FrequencyDto.VERY_RARE,
                FrequencyDto.RARE,
                FrequencyDto.NORMAL,
                FrequencyDto.FREQUENT,
                FrequencyDto.VERY_FREQUENT,
                FrequencyDto.UNKNOWN
            )
        }
    }
}