package jp.neechan.akari.dictionary.discover

import jp.neechan.akari.dictionary.word.WordDTO
import retrofit2.http.GET
import retrofit2.http.HeaderMap
import retrofit2.http.Path
import retrofit2.http.QueryMap

interface WordsApiService {

    // todo: OkHttp interceptor to add the authorization token.
    @GET("words/")
    suspend fun loadWords(@HeaderMap headers: Map<String, String>,
                          @QueryMap parameters: Map<String, String>): Page<String>

    @GET("words/{word}")
    suspend fun loadWord(@HeaderMap headers: Map<String, String>,
                         @Path("word") word: String): WordDTO
}