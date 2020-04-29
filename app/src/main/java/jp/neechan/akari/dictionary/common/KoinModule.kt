package jp.neechan.akari.dictionary.common

import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import jp.neechan.akari.dictionary.discover.WordsApiService
import jp.neechan.akari.dictionary.discover.WordsRemoteRepository
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object KoinModule {

    val module = module {
        single { StringPageDeserializer() }

        single {
            val stringPageType = object : TypeToken<Page<String>>(){}.type
            val stringPageDeserializer: StringPageDeserializer = get()
            GsonBuilder().registerTypeAdapter(stringPageType, stringPageDeserializer)
                         .create()
        }

        single {
            Retrofit.Builder()
                    .baseUrl("https://wordsapiv1.p.rapidapi.com/")
                    .addConverterFactory(GsonConverterFactory.create(get()))
                    .build()
        }

        single {
            val retrofit: Retrofit = get()
            retrofit.create(WordsApiService::class.java)
        }

        single { WordsRemoteRepository(get()) }

        single { ViewModelFactory(get()) }
    }
}