package jp.neechan.akari.dictionary.base.data.framework.network

import jp.neechan.akari.dictionary.base.data.framework.dto.WordDto
import jp.neechan.akari.dictionary.base.data.interface_adapters.WordsRemoteSource
import jp.neechan.akari.dictionary.base.domain.entities.Page
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.QueryMap

interface WordsApiService : WordsRemoteSource {

    @GET("words/")
    override suspend fun loadWords(@QueryMap params: Map<String, String>): Page<String>

    @GET("words/{word}")
    override suspend fun loadWord(@Path("word") word: String): WordDto
}