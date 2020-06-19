package jp.neechan.akari.dictionary.core_impl.data.framework.network

import jp.neechan.akari.dictionary.core_impl.BuildConfig
import jp.neechan.akari.dictionary.test_utils.mockito.MockitoUtils.anyNonNull
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import org.junit.Rule
import org.junit.Test
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoRule

internal class AuthorizationInterceptorTest {

    @get:Rule
    val mockitoRule: MockitoRule = MockitoJUnit.rule()

    @Mock private lateinit var mockChain: Interceptor.Chain
    @Mock private lateinit var mockRequest: Request
    @Mock private lateinit var mockRequestBuilder: Request.Builder
    @Mock private lateinit var mockResponse: Response

    private val interceptorUnderTest = AuthorizationInterceptor()

    @Test
    fun `should add api key to headers`() {
        val expectedApiKeyName = "X-Mashape-Key"
        val expectedApiKeyValue = BuildConfig.API_KEY

        `when`(mockChain.request()).thenReturn(mockRequest)
        `when`(mockRequest.newBuilder()).thenReturn(mockRequestBuilder)
        `when`(mockRequestBuilder.header(anyString(), anyString())).thenReturn(mockRequestBuilder)
        `when`(mockRequestBuilder.build()).thenReturn(mockRequest)
        `when`(mockChain.proceed(anyNonNull())).thenReturn(mockResponse)

        interceptorUnderTest.intercept(mockChain)

        verify(mockRequestBuilder).header(expectedApiKeyName, expectedApiKeyValue)
    }
}