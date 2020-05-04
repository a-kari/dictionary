package jp.neechan.akari.dictionary.common.di

import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import jp.neechan.akari.dictionary.common.viewmodels.ViewModelFactory
import jp.neechan.akari.dictionary.discover.Page
import jp.neechan.akari.dictionary.discover.StringPageDeserializer
import jp.neechan.akari.dictionary.discover.WordsApiService
import jp.neechan.akari.dictionary.discover.WordsRemoteRepository
import jp.neechan.akari.dictionary.word.*
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object KoinModule {

    val module = module {
        single { StringPageDeserializer() }
        single { WordDeserializer() }
        single { DefinitionDeserializer() }

        single {
            val stringPageType = object : TypeToken<Page<String>>(){}.type
            val stringPageDeserializer: StringPageDeserializer = get()
            val wordDeserializer: WordDeserializer = get()
            val definitionDeserializer: DefinitionDeserializer = get()

            GsonBuilder().registerTypeAdapter(stringPageType, stringPageDeserializer)
                         .registerTypeAdapter(WordDTO::class.java, wordDeserializer)
                         .registerTypeAdapter(DefinitionDTO::class.java, definitionDeserializer)
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

        single { DefinitionMapper() }
        single { WordMapper(get()) }

        single { WordsRemoteRepository(get(), get()) }

        single {
            ViewModelFactory(
                get()
            )
        }
    }
}