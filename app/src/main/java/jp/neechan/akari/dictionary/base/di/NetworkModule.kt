package jp.neechan.akari.dictionary.base.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.Reusable
import jp.neechan.akari.dictionary.base.data.framework.dto.WordDto
import jp.neechan.akari.dictionary.base.data.framework.network.AuthorizationInterceptor
import jp.neechan.akari.dictionary.base.data.framework.network.PageDeserializer
import jp.neechan.akari.dictionary.base.data.framework.network.RetrofitResultWrapper
import jp.neechan.akari.dictionary.base.data.framework.network.WordDtoDeserializer
import jp.neechan.akari.dictionary.base.data.framework.network.WordsApi
import jp.neechan.akari.dictionary.base.data.framework.network.WordsRemoteSourceImpl
import jp.neechan.akari.dictionary.base.data.interface_adapters.ResultWrapper
import jp.neechan.akari.dictionary.base.data.interface_adapters.WordsRemoteSource
import jp.neechan.akari.dictionary.base.di.qualifiers.BaseUrl
import jp.neechan.akari.dictionary.base.domain.entities.Page
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
abstract class NetworkModule {

    companion object {

        @Provides
        @Reusable
        @BaseUrl
        @JvmStatic
        fun provideBaseUrl() = "https://wordsapiv1.p.rapidapi.com/"

        @Provides
        @Reusable
        @JvmStatic
        fun provideGson(stringPageDeserializer: PageDeserializer<String>, wordDtoDeserializer: WordDtoDeserializer): Gson {
            val stringPageType = object : TypeToken<Page<String>>() {}.type
            return GsonBuilder().registerTypeAdapter(stringPageType, stringPageDeserializer)
                                .registerTypeAdapter(WordDto::class.java, wordDtoDeserializer)
                                .create()
        }

        @Provides
        @Singleton
        @JvmStatic
        fun provideOkHttpClient(authorizationInterceptor: AuthorizationInterceptor): OkHttpClient {
            return OkHttpClient.Builder()
                               .addNetworkInterceptor(authorizationInterceptor)
                               .build()
        }

        @Provides
        @Singleton
        @JvmStatic
        fun provideRetrofit(okHttpClient: OkHttpClient, @BaseUrl baseUrl: String, gson: Gson): Retrofit {
            return Retrofit.Builder()
                           .client(okHttpClient)
                           .baseUrl(baseUrl)
                           .addConverterFactory(GsonConverterFactory.create(gson))
                           .build()
        }

        @Provides
        @Reusable
        @JvmStatic
        fun provideWordsApi(retrofit: Retrofit): WordsApi {
            return retrofit.create(WordsApi::class.java)
        }
    }

    @Binds
    abstract fun bindResultWrapper(retrofitResultWrapper: RetrofitResultWrapper): ResultWrapper

    @Binds
    abstract fun bindWordsRemoteSource(wordsRemoteSourceImpl: WordsRemoteSourceImpl): WordsRemoteSource
}