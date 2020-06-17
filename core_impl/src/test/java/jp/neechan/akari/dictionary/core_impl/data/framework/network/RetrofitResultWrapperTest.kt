package jp.neechan.akari.dictionary.core_impl.data.framework.network

import jp.neechan.akari.dictionary.core_api.domain.entities.Result
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import retrofit2.HttpException
import java.net.HttpURLConnection
import java.net.UnknownHostException

class RetrofitResultWrapperTest {

    private val wrapperUnderTest = RetrofitResultWrapper()

    @Test
    fun `should return Success`() = runBlockingTest {
        val inputBlock = { listOf("Giraffe", "Leopard", "Zebra") }
        val expectedResult = Result.Success(inputBlock())

        val actualResult = wrapperUnderTest.wrapWithResult { inputBlock() }

        assertEquals(expectedResult, actualResult)
    }

    @Test
    fun `should return ConnectionError`() = runBlockingTest {
        val inputBlock = { throw UnknownHostException() }
        val expectedResult = Result.ConnectionError

        val actualResult = wrapperUnderTest.wrapWithResult { inputBlock() }

        assertEquals(expectedResult, actualResult)
    }

    @Test
    fun `should return NotFoundError`() = runBlockingTest {
        val mockHttpException = mock(HttpException::class.java)
        `when`(mockHttpException.code()).thenReturn(HttpURLConnection.HTTP_NOT_FOUND)
        val inputBlock = { throw mockHttpException }
        val expectedResult = Result.NotFoundError

        val actualResult = wrapperUnderTest.wrapWithResult { inputBlock() }

        assertEquals(expectedResult, actualResult)
    }

    @Test
    fun `should return NotFoundError 2`() = runBlockingTest {
        val inputBlock = { null }
        val expectedResult = Result.NotFoundError

        val actualResult = wrapperUnderTest.wrapWithResult { inputBlock() }

        assertEquals(expectedResult, actualResult)
    }

    @Test
    fun `should return Error`() = runBlockingTest {
        val exception = RuntimeException("Something went wrong")
        val inputBlock = { throw exception }
        val expectedResult = Result.Error(exception)

        val actualResult = wrapperUnderTest.wrapWithResult { inputBlock() }

        assertTrue(actualResult is Result.Error)
        assertEquals(expectedResult.error, (actualResult as Result.Error).error)
    }
}