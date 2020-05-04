package jp.neechan.akari.dictionary.discover

import retrofit2.http.GET
import retrofit2.http.HeaderMap
import retrofit2.http.QueryMap

interface WordsApiService {

    // todo: OkHttp interceptor to add the authorization token.
    @GET("words/")
    suspend fun loadWords(@HeaderMap headers: Map<String, String>,
                          @QueryMap parameters: Map<String, String>): Page<String>
}