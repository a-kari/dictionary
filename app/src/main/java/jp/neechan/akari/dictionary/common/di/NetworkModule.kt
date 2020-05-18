package jp.neechan.akari.dictionary.common.di

import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import jp.neechan.akari.dictionary.common.models.dto.WordDto
import jp.neechan.akari.dictionary.common.models.models.Page
import jp.neechan.akari.dictionary.common.network.AuthorizationInterceptor
import jp.neechan.akari.dictionary.common.network.StringPageDeserializer
import jp.neechan.akari.dictionary.common.network.WordDeserializer
import jp.neechan.akari.dictionary.discover.WordsApiService
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NetworkModule : KoinModule {

    override fun get() = module {
        // TypeAdapters
        single { StringPageDeserializer() }
        single { WordDeserializer() }

        // Interceptors
        single { AuthorizationInterceptor() }

        // Gson
        single {
            val stringPageType = object : TypeToken<Page<String>>(){}.type
            GsonBuilder().registerTypeAdapter(stringPageType, get(StringPageDeserializer::class))
                         .registerTypeAdapter(WordDto::class.java, get(WordDeserializer::class))
                         .create()
        }

        // OkHttpClient
        single {
            OkHttpClient.Builder()
                        .addNetworkInterceptor(get(AuthorizationInterceptor::class))
                        .build()
        }

        // Retrofit
        single {
            Retrofit.Builder()
                    .client(get())
                    .baseUrl("https://wordsapiv1.p.rapidapi.com/")
                    .addConverterFactory(GsonConverterFactory.create(get()))
                    .build()
        }

        // Network services
        single {
            val retrofit: Retrofit = get()
            retrofit.create(WordsApiService::class.java)
        }
    }
}