package jp.neechan.akari.dictionary.core_impl.data.framework.network

import jp.neechan.akari.dictionary.core_api.domain.entities.Page
import jp.neechan.akari.dictionary.core_impl.data.framework.dto.WordDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.QueryMap

interface WordsApi {

    @GET("words/")
    suspend fun loadWords(@QueryMap params: Map<String, String>): Page<String>

    @GET("words/{word}")
    suspend fun loadWord(@Path("word") word: String): WordDto
}