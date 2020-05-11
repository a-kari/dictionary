package jp.neechan.akari.dictionary.discover

import jp.neechan.akari.dictionary.common.models.models.Page
import jp.neechan.akari.dictionary.common.models.dto.WordDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.QueryMap

interface WordsApiService {

    @GET("words/")
    suspend fun loadWords(@QueryMap params: Map<String, String>): Page<String>

    @GET("words/{word}")
    suspend fun loadWord(@Path("word") word: String): WordDto
}