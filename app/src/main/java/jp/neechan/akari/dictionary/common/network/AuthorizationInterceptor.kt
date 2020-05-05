package jp.neechan.akari.dictionary.common.network

import okhttp3.Interceptor
import okhttp3.Response

class AuthorizationInterceptor : Interceptor {

    companion object {
        private const val AUTHORIZATION_TOKEN_KEY = "X-Mashape-Key"
        private const val AUTHORIZATION_TOKEN_VALUE = "8395b7db36mshce928fb38d86eb8p1e163bjsn8070f6b090a6"
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val authenticatedRequest = request.newBuilder()
                                          .header(AUTHORIZATION_TOKEN_KEY, AUTHORIZATION_TOKEN_VALUE)
                                          .build()
        return chain.proceed(authenticatedRequest)
    }
}