package jp.neechan.akari.dictionary.core_impl.data.framework.network

import jp.neechan.akari.dictionary.core_api.domain.entities.Page
import jp.neechan.akari.dictionary.core_api.domain.entities.Result
import jp.neechan.akari.dictionary.core_impl.data.framework.dto.FrequencyDto
import jp.neechan.akari.dictionary.core_impl.data.framework.dto.WordDto
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import retrofit2.HttpException
import java.net.HttpURLConnection
import java.net.UnknownHostException
import java.util.Date

@RunWith(Parameterized::class)
class RetrofitResultWrapperTest<T>(private val inputBlock: () -> T?,
                                   private val expectedResult: Result<T>) {

    private val wrapperUnderTest = RetrofitResultWrapper()

    @Test
    fun `should return expected Result according to return of the block`() = runBlockingTest {
        val actualResult = wrapperUnderTest.wrapWithResult { inputBlock() }

        if (expectedResult is Result.Success || expectedResult is Result.NotFoundError || expectedResult is Result.ConnectionError) {
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

            val connectionException = UnknownHostException()
            val notFoundException = mock(HttpException::class.java).also { `when`(it.code()).thenReturn(HttpURLConnection.HTTP_NOT_FOUND) }
            val unknownException = RuntimeException("Something went wrong")

            return listOf(
                arrayOf<Any>({ sampleStringList },          Result.Success(sampleStringList)),
                arrayOf<Any>({ samplePage },                Result.Success(samplePage)),
                arrayOf<Any>({ sampleWordDto },             Result.Success(sampleWordDto)),
                arrayOf<Any>({ emptyStringList },           Result.NotFoundError),
                arrayOf<Any>({ emptyPage },                 Result.NotFoundError),
                arrayOf<Any>({ null },                      Result.NotFoundError),
                arrayOf<Any>({ throw notFoundException },   Result.NotFoundError),
                arrayOf<Any>({ throw connectionException }, Result.ConnectionError),
                arrayOf<Any>({ throw unknownException },    Result.Error(unknownException))
            )
        }
    }
}