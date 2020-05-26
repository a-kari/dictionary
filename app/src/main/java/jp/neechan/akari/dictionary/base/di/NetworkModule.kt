package jp.neechan.akari.dictionary.base.di

import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import jp.neechan.akari.dictionary.base.data.framework.dto.WordDto
import jp.neechan.akari.dictionary.base.data.framework.dto.mappers.FilterParamsToFilterParamsDtoMapper
import jp.neechan.akari.dictionary.base.data.framework.dto.mappers.WordToWordDtoMapper
import jp.neechan.akari.dictionary.base.data.framework.network.AuthorizationInterceptor
import jp.neechan.akari.dictionary.base.data.framework.network.RetrofitResultWrapper
import jp.neechan.akari.dictionary.base.data.framework.network.StringPageDeserializer
import jp.neechan.akari.dictionary.base.data.framework.network.WordDeserializer
import jp.neechan.akari.dictionary.base.data.framework.network.WordsApi
import jp.neechan.akari.dictionary.base.data.framework.network.WordsRemoteSourceImpl
import jp.neechan.akari.dictionary.base.data.interface_adapters.ResultWrapper
import jp.neechan.akari.dictionary.base.data.interface_adapters.WordsRemoteSource
import jp.neechan.akari.dictionary.base.domain.entities.Page
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
            retrofit.create(WordsApi::class.java)
        }

        // ResultWrapper
        single { RetrofitResultWrapper() as ResultWrapper }

        single {
            WordsRemoteSourceImpl(
                get(),
                get(WordToWordDtoMapper::class),
                get(FilterParamsToFilterParamsDtoMapper::class)
            ) as WordsRemoteSource
        }
    }
}