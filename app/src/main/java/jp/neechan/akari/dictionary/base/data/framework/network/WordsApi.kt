package jp.neechan.akari.dictionary.base.data.framework.network

import jp.neechan.akari.dictionary.base.data.framework.dto.WordDto
import jp.neechan.akari.dictionary.base.domain.entities.Page
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.QueryMap

interface WordsApi {

    @GET("words/")
    suspend fun loadWords(@QueryMap params: Map<String, String>): Page<String>

    @GET("words/{word}")
    suspend fun loadWord(@Path("word") word: String): WordDto
}