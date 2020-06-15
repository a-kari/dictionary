package jp.neechan.akari.dictionary.core_impl.data.framework.db.typeconverters

import jp.neechan.akari.dictionary.core_impl.data.framework.db.typeconverters.StringListTypeConverter
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized

@RunWith(Parameterized::class)
class StringListTypeConverterTest(private val inputList: List<String>?) {

    // The test calls two methods of the unit (save() & restore()).
    // But it doesn't matter in what form the unit saves the list -
    // actually, we just need to check that a restored list equals to original.
    @Test
    fun `restored list should be equal to input`() {
        val expectedList = inputList

        val actualList = StringListTypeConverter.restore(StringListTypeConverter.save(inputList))

        assertEquals(expectedList, actualList)
    }

    companion object {
        @JvmStatic
        @Parameterized.Parameters
        fun getParams(): List<List<String>?> {
            return listOf(
                listOf("Giraffe", "Leopard", "Zebra"),
                listOf("Giraffe", "Leopard"),
                listOf("Giraffe"),
                emptyList(),
                null
            )
        }
    }
}