package jp.neechan.akari.dictionary.core_api.domain

import jp.neechan.akari.dictionary.core_api.domain.entities.Page
import org.hamcrest.CoreMatchers.instanceOf
import org.junit.Assert.assertEquals
import org.junit.Assert.assertThat
import org.junit.Assert.fail
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

    @RunWith(Parameterized::class)
    class IterableTest(private val pageUnderTest: Page<String>,
                       private val expectedElements: List<String>) {

        @Test
        fun `test iterator()`() {
            val expectedIterator = expectedElements.iterator()
            val actualIterator = pageUnderTest.iterator()

            for (i in expectedElements.indices) {
                assertEquals(expectedIterator.hasNext(), actualIterator.hasNext())

                if (expectedIterator.hasNext()) {
                    assertEquals(expectedIterator.next(), actualIterator.next())
                }
            }
        }

        companion object {
            @JvmStatic
            @Parameterized.Parameters
            fun getParams(): Collection<Array<Iterable<String>>> = listOf(
                arrayOf(Page(listOf("Giraffe", "Leopard", "Zebra"), 1, false), listOf("Giraffe", "Leopard", "Zebra")),
                arrayOf(Page(listOf("Giraffe", "Leopard"), 1, false), listOf("Giraffe", "Leopard")),
                arrayOf(Page(listOf("Giraffe"), 1, false), listOf("Giraffe")),
                arrayOf(Page(emptyList<String>(), 1, false), emptyList<String>())
            )
        }
    }

    @RunWith(Parameterized::class)
    class GetTest(private val pageUnderTest: Page<String>,
                  private val inputIndex: Int,
                  private val expectedElement: String) {

        @Test
        fun `test get()`() {
            val actualElement = pageUnderTest[inputIndex]

            assertEquals(expectedElement, actualElement)
        }

        companion object {
            @JvmStatic
            @Parameterized.Parameters
            fun getParams(): Collection<Array<Any>> {
                val samplePage = Page(listOf("Giraffe", "Leopard", "Zebra"), 1, false)

                return listOf(
                    arrayOf(samplePage, 0, "Giraffe"),
                    arrayOf(samplePage, 1, "Leopard"),
                    arrayOf(samplePage, 2, "Zebra")
                )
            }
        }
    }

    @RunWith(Parameterized::class)
    class GetExceptionTest(private val pageUnderTest: Page<String>,
                           private val inputIndex: Int) {

        @Test
        fun `should throw IndexOutOfBoundsException`() {
            try {
                pageUnderTest[inputIndex]
                fail("Expected IndexOutOfBoundsException, but it wasn't thrown")

            } catch (t: Throwable) {
                assertThat(t, instanceOf(IndexOutOfBoundsException::class.java))
            }
        }

        companion object {
            @JvmStatic
            @Parameterized.Parameters
            fun getParams(): Collection<Array<Any>> {
                val samplePage = Page(listOf("Giraffe", "Leopard", "Zebra"), 1, false)
                val sampleEmptyPage = Page(emptyList<String>(), 1, false)

                return listOf(
                    arrayOf(samplePage, -1),
                    arrayOf(samplePage, 3),
                    arrayOf(sampleEmptyPage, -1),
                    arrayOf(sampleEmptyPage, 0),
                    arrayOf(sampleEmptyPage, 1)
                )
            }
        }
    }
}