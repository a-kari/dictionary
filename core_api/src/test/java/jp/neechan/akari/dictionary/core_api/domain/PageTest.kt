package jp.neechan.akari.dictionary.core_api.domain

import jp.neechan.akari.dictionary.core_api.domain.entities.Page
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.experimental.runners.Enclosed
import org.junit.runner.RunWith
import org.junit.runners.Parameterized

@RunWith(Enclosed::class)
class PageTest {

    @RunWith(Parameterized::class)
    class ConstructorTests(private val inputContent: List<String>,
                           private val inputPageNumber: Int,
                           inputLimit: Int,
                           inputTotal: Int,
                           private val expectedHasNextPage: Boolean) {

        private val pageUnderTest = Page(inputContent, inputPageNumber, inputLimit, inputTotal)

        @Test
        fun `secondary constructor should correctly init properties`() {
            assertEquals(inputContent, pageUnderTest.content)
            assertEquals(inputPageNumber, pageUnderTest.pageNumber)
            assertEquals(expectedHasNextPage, pageUnderTest.hasNextPage)
        }

        companion object {
            @JvmStatic
            @Parameterized.Parameters
            fun getParams() : Collection<Array<Any>> {
                return listOf(
                    // content                                 pageNumber limit total hasNextPage
                    arrayOf(listOf("Giraffe", "Leopard", "Zebra"), 1,       10,   3,     false),
                    arrayOf(listOf("Giraffe", "Leopard", "Zebra"), 1,       3,    100,   true),
                    arrayOf(emptyList<String>(),                   1,       10,   0,     false),
                    arrayOf(emptyList<String>(),                   5,       10,   49,    false)
                )
            }
        }
    }

    @RunWith(Parameterized::class)
    class MethodTests(private val pageUnderTest: Page<String>,
                      private val expectedIsEmpty: Boolean,
                      private val expectedIsNotEmpty: Boolean) {

        @Test
        fun `test isEmpty()`() {
            val actualIsEmpty = pageUnderTest.isEmpty()

            assertEquals(expectedIsEmpty, actualIsEmpty)
        }

        @Test
        fun `test isNotEmpty()`() {
            val actualIsNotEmpty = pageUnderTest.isNotEmpty()

            assertEquals(expectedIsNotEmpty, actualIsNotEmpty)
        }

        companion object {
            @JvmStatic
            @Parameterized.Parameters
            fun getParams() : Collection<Array<Any>> {
                return listOf(
                    // pageUnderTest                                             isEmpty isNotEmpty
                    arrayOf(Page(listOf("Giraffe", "Leopard", "Zebra"), 1, true), false, true),
                    arrayOf(Page(emptyList<String>(), 1, false),                  true,  false)
                )
            }
        }
    }
}