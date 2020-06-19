package jp.neechan.akari.dictionary.core_impl.presentation.models.mappers

import jp.neechan.akari.dictionary.core_api.domain.entities.Frequency
import jp.neechan.akari.dictionary.core_api.presentation.models.FrequencyUI
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized

@RunWith(Parameterized::class)
class FrequencyToFrequencyUIMapperTest(private val frequency: Frequency,
                                       private val frequencyUI: FrequencyUI) {

    private val mapperUnderTest = FrequencyToFrequencyUIMapper()

    @Test
    fun `test mapToInternalLayer()`() {
        val inputFrequencyUI = frequencyUI
        val expectedFrequency = frequency

        val actualFrequency = mapperUnderTest.mapToInternalLayer(inputFrequencyUI)

        assertEquals(expectedFrequency, actualFrequency)
    }

    @Test
    fun `test mapToExternalLayer()`() {
        val inputFrequency = frequency
        val expectedFrequencyUI = frequencyUI

        val actualFrequencyUI = mapperUnderTest.mapToExternalLayer(inputFrequency)

        assertEquals(expectedFrequencyUI, actualFrequencyUI)
    }

    companion object {
        @JvmStatic
        @Parameterized.Parameters
        fun getParams(): Collection<Array<Enum<*>>> {
            return listOf(
                arrayOf(Frequency.VERY_RARE,     FrequencyUI.VERY_RARE),
                arrayOf(Frequency.RARE,          FrequencyUI.RARE),
                arrayOf(Frequency.NORMAL,        FrequencyUI.NORMAL),
                arrayOf(Frequency.FREQUENT,      FrequencyUI.FREQUENT),
                arrayOf(Frequency.VERY_FREQUENT, FrequencyUI.VERY_FREQUENT),
                arrayOf(Frequency.UNKNOWN,       FrequencyUI.UNKNOWN)
            )
        }
    }
}