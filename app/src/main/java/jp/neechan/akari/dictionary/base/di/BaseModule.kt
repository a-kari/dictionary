package jp.neechan.akari.dictionary.base.di

import androidx.room.Room
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import jp.neechan.akari.dictionary.base.data.framework.network.AuthorizationInterceptor
import jp.neechan.akari.dictionary.base.data.framework.network.PageDeserializer
import jp.neechan.akari.dictionary.base.data.framework.network.WordDtoDeserializer
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidApplication
import org.koin.core.module.Module
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object BaseModule : KoinModule {

    const val resultPageStringToUIStatePageStringMapper = "resultPageStringToUIStatePageStringMapper"
    const val resultWordToUIStateWordUIMapper = "resultWordToUIStateWordUIMapper"

    override val module = module {
        provideDatabase()
        provideNetwork()
        provideSharedPreferences()
        provideTts()
        provideRepositories()
        provideDtoMappers()
        provideUiMappers()
        provideUseCases()
    }

    private fun Module.provideDtoMappers() {
        single { FrequencyToFrequencyDtoMapper() }

        single { PartOfSpeechToPartOfSpeechDtoMapper() }

        single {
            WordToWordDtoMapper(
                get(FrequencyToFrequencyDtoMapper::class),
                get(DefinitionToDefinitionDtoMapper::class)
            )
        }

        single { DefinitionToDefinitionDtoMapper(get(PartOfSpeechToPartOfSpeechDtoMapper::class)) }

        single {
            FilterParamsToFilterParamsDtoMapper(
                get(FrequencyToFrequencyDtoMapper::class),
                get(PartOfSpeechToPartOfSpeechDtoMapper::class)
            )
        }
    }

    private fun Module.provideUiMappers() {
        single {
            WordToWordUIMapper(
                get(FrequencyToFrequencyUIMapper::class),
                get(PartOfSpeechToPartOfSpeechUIMapper::class),
                get(DefinitionToDefinitionUIMapper::class)
            )
        }

        single { DefinitionToDefinitionUIMapper(get(PartOfSpeechToPartOfSpeechUIMapper::class)) }

        single { FrequencyToFrequencyUIMapper() }

        single { PartOfSpeechToPartOfSpeechUIMapper() }

        single(named(resultPageStringToUIStatePageStringMapper)) {
            ResultToUIStateMapper<Page<String>, Page<String>>()
        }

        single(named(resultWordToUIStateWordUIMapper)) {
            ResultToUIStateMapper<Word, WordUI>(get(WordToWordUIMapper::class))
        }
    }

    private fun Module.provideDatabase() {
        single {
            Room.databaseBuilder(
                androidApplication(),
                DatabaseService::class.java,
                getProperty(BaseProperties.DATABASE_NAME)
            ).build()
        }

        single { get<DatabaseService>().getWordDao() }

        single<WordsLocalSource> { WordsLocalSourceImpl(get(), get(WordToWordDtoMapper::class)) }
    }

    private fun Module.provideNetwork() {
        // TypeAdapters
        single { PageDeserializer<String>() }
        single { WordDtoDeserializer() }

        // Interceptors
        single { AuthorizationInterceptor() }

        // Gson
        single {
            val stringPageType = object : TypeToken<Page<String>>() {}.type
            GsonBuilder().registerTypeAdapter(stringPageType, get(PageDeserializer::class))
                         .registerTypeAdapter(WordDto::class.java, get(WordDtoDeserializer::class))
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
                    .baseUrl(getProperty<String>(BaseProperties.BASE_URL))
                    .addConverterFactory(GsonConverterFactory.create(get()))
                    .build()
        }

        // Network services
        single { get<Retrofit>().create(WordsApi::class.java) }

        // ResultWrapper
        single<ResultWrapper> { RetrofitResultWrapper() }

        // Interfaces for interface adapters
        single<WordsRemoteSource> {
            WordsRemoteSourceImpl(
                get(),
                get(WordToWordDtoMapper::class),
                get(FilterParamsToFilterParamsDtoMapper::class)
            )
        }
    }

    private fun Module.provideRepositories() {
        single<WordsRepository> { WordsRepositoryImpl(get(), get(), get()) }

        single<FilterParamsRepository> { FilterParamsRepositoryImpl(get()) }

        single<TextToSpeechPreferencesRepository> { TextToSpeechPreferencesRepositoryImpl(get()) }
    }

    private fun Module.provideSharedPreferences() {
        single<FilterPreferencesService> { FilterPreferencesServiceImpl(androidApplication()) }

        single<TextToSpeechPreferencesService> { TextToSpeechPreferencesServiceImpl(androidApplication()) }
    }

    private fun Module.provideTts() {
        single<TextToSpeechService> { TextToSpeechServiceImpl(androidApplication(), get()) }
    }

    private fun Module.provideUseCases() {
        single { LoadWordUseCase(get()) }

        single { LoadFilterParamsUseCase(get()) }

        single { SpeakUseCase(get()) }

        single { StopSpeakingUseCase(get()) }
    }
}