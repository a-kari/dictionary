package jp.neechan.akari.dictionary.core_impl.data.framework.db.typeconverters

import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized
import java.util.Date

@RunWith(Parameterized::class)
class DateTypeConverterTest(private val inputDate: Date?) {

    private val converterUnderTest = DateTypeConverter()

    // The test calls two methods of the unit (save() & restore()).
    // But it doesn't matter in what form the unit saves the date -
    // actually, we just need to check that a restored date equals to original.
    @Test
    fun `restored date should be equal to input`() {
        val expectedDate = inputDate

        val actualDate = converterUnderTest.restore(converterUnderTest.save(inputDate))

        assertEquals(expectedDate, actualDate)
    }

    companion object {
        @JvmStatic
        @Parameterized.Parameters
        fun getParams(): List<Date?> {
            return listOf(
                Date(),
                Date(0L),
                null
            )
        }
    }
}