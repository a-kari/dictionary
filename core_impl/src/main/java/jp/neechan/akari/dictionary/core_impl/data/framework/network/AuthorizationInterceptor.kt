package jp.neechan.akari.dictionary.core_impl.data.framework.network

import dagger.Reusable
import jp.neechan.akari.dictionary.core_impl.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

@Reusable
internal class AuthorizationInterceptor @Inject constructor() : Interceptor {

    companion object {
        private const val API_KEY_HEADER = "X-Mashape-Key"
        private const val API_KEY = BuildConfig.API_KEY
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val authenticatedRequest = request.newBuilder()
                                          .header(API_KEY_HEADER, API_KEY)
                                          .build()
        return chain.proceed(authenticatedRequest)
    }
}
