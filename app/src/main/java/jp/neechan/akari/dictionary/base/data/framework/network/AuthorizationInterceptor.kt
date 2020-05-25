package jp.neechan.akari.dictionary.base.data.framework.network

import jp.neechan.akari.dictionary.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

class AuthorizationInterceptor : Interceptor {

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