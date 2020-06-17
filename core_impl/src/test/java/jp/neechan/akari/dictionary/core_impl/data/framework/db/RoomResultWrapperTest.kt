package jp.neechan.akari.dictionary.core_impl.data.framework.db

import jp.neechan.akari.dictionary.core_api.domain.entities.Page
import jp.neechan.akari.dictionary.core_api.domain.entities.Result
import jp.neechan.akari.dictionary.core_impl.data.framework.dto.FrequencyDto
import jp.neechan.akari.dictionary.core_impl.data.framework.dto.WordDto
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized
import java.util.Date

@RunWith(Parameterized::class)
class RoomResultWrapperTest<T>(private val inputBlock: () -> T?,
                               private val expectedResult: Result<T>) {

    private val wrapperUnderTest = RoomResultWrapper()

    @Test
    fun `should return expected Result according to return of the block`() = runBlockingTest {
        val actualResult = wrapperUnderTest.wrapWithResult { inputBlock() }

        if (expectedResult is Result.Success || expectedResult is Result.NotFoundError) {
            assertEquals(expectedResult, actualResult)

        } else {
            assertEquals(expectedResult::class, actualResult::class)
            expectedResult as Result.Error
            actualResult as Result.Error
            assertEquals(expectedResult.error!!::class, actualResult.error!!::class)
            assertEquals(expectedResult.error!!.message, actualResult.error!!.message)
        }
    }

    companion object {
        @JvmStatic
        @Parameterized.Parameters
        fun getParams(): Collection<Array<out Any>> {
            val sampleStringList = listOf("Giraffe", "Leopard", "Zebra")
            val samplePage = Page(sampleStringList, 1, false)
            val sampleWordDto = WordDto("hello", "həˈləʊ", listOf("hel", "lo"), FrequencyDto.FREQUENT, null, Date(10))

            val emptyStringList = emptyList<String>()
            val emptyPage = Page(emptyStringList, 1, false)
            val sampleException = RuntimeException("Something went wrong")

            return listOf(
                arrayOf<Any>({ sampleStringList },      Result.Success(sampleStringList)),
                arrayOf<Any>({ samplePage },            Result.Success(samplePage)),
                arrayOf<Any>({ sampleWordDto },         Result.Success(sampleWordDto)),
                arrayOf<Any>({ emptyStringList },       Result.NotFoundError),
                arrayOf<Any>({ emptyPage },             Result.NotFoundError),
                arrayOf<Any>({ null },                  Result.NotFoundError),
                arrayOf<Any>({ throw sampleException }, Result.Error(sampleException))
            )
        }
    }
}