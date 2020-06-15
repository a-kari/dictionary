package jp.neechan.akari.dictionary.core_impl.data.framework.dto

import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized

@RunWith(Parameterized::class)
internal class FrequencyDtoTest(private val inputFrequencyNumber: Float?,
                                private val expectedFrequency: FrequencyDto) {

    @Test
    fun `test valueOf(frequencyNumber)`() {
        val actualFrequency = FrequencyDto.valueOf(inputFrequencyNumber)
        assertEquals(expectedFrequency, actualFrequency)
    }

    companion object {
        @JvmStatic
        @Parameterized.Parameters
        fun getParams(): Collection<Array<out Any?>> {
            return listOf(
                arrayOf(0f,   FrequencyDto.VERY_RARE),
                arrayOf(1f,   FrequencyDto.VERY_RARE),
                arrayOf(2f,   FrequencyDto.RARE),
                arrayOf(3f,   FrequencyDto.RARE),
                arrayOf(4f,   FrequencyDto.NORMAL),
                arrayOf(4.5f, FrequencyDto.NORMAL),
                arrayOf(5f,   FrequencyDto.FREQUENT),
                arrayOf(5.5f, FrequencyDto.FREQUENT),
                arrayOf(6f,   FrequencyDto.VERY_FREQUENT),
                arrayOf(7f,   FrequencyDto.VERY_FREQUENT),
                arrayOf(8f,   FrequencyDto.VERY_FREQUENT),
                arrayOf(9f,   FrequencyDto.VERY_FREQUENT),
                arrayOf(10f,  FrequencyDto.UNKNOWN),
                arrayOf(-1f,  FrequencyDto.UNKNOWN),
                arrayOf(null, FrequencyDto.UNKNOWN)
            )
        }
    }
}