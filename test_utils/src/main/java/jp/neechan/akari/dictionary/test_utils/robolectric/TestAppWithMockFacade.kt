package jp.neechan.akari.dictionary.test_utils.robolectric

import android.app.Application
import android.content.Context
import jp.neechan.akari.dictionary.core_api.di.AppWithFacade
import jp.neechan.akari.dictionary.core_api.di.ProvidersFacade
import jp.neechan.akari.dictionary.core_api.domain.entities.Frequency
import jp.neechan.akari.dictionary.core_api.domain.entities.Page
import jp.neechan.akari.dictionary.core_api.domain.entities.PartOfSpeech
import jp.neechan.akari.dictionary.core_api.domain.entities.Result
import jp.neechan.akari.dictionary.core_api.domain.entities.Word
import jp.neechan.akari.dictionary.core_api.domain.entities.mappers.ModelMapper
import jp.neechan.akari.dictionary.core_api.domain.usecases.FilterParamsRepository
import jp.neechan.akari.dictionary.core_api.domain.usecases.TextToSpeechPreferencesRepository
import jp.neechan.akari.dictionary.core_api.domain.usecases.TextToSpeechService
import jp.neechan.akari.dictionary.core_api.domain.usecases.WordsRepository
import jp.neechan.akari.dictionary.core_api.presentation.mediators.DiscoverMediator
import jp.neechan.akari.dictionary.core_api.presentation.mediators.HomeMediator
import jp.neechan.akari.dictionary.core_api.presentation.mediators.SearchWordMediator
import jp.neechan.akari.dictionary.core_api.presentation.mediators.SettingsMediator
import jp.neechan.akari.dictionary.core_api.presentation.mediators.WordMediator
import jp.neechan.akari.dictionary.core_api.presentation.mediators.WordsFilterMediator
import jp.neechan.akari.dictionary.core_api.presentation.models.FrequencyUI
import jp.neechan.akari.dictionary.core_api.presentation.models.PartOfSpeechUI
import jp.neechan.akari.dictionary.core_api.presentation.models.UIState
import jp.neechan.akari.dictionary.core_api.presentation.models.WordUI
import jp.neechan.akari.dictionary.test_utils.R
import org.mockito.Mockito.mock

/**
 * This class mocks AppWithFacade's provisions.
 * Mocked provisions' behavior can be overridden in tests using below fields.
 */
class TestAppWithMockFacade : Application(), AppWithFacade {

    private val facade: ProvidersFacade

    val context: Context = mock(Context::class.java)
    val homeMediator: HomeMediator = mock(HomeMediator::class.java)
    val discoverMediator: DiscoverMediator = mock(DiscoverMediator::class.java)
    val settingsMediator: SettingsMediator = mock(SettingsMediator::class.java)
    val wordMediator: WordMediator = mock(WordMediator::class.java)
    val searchWordMediator: SearchWordMediator = mock(SearchWordMediator::class.java)
    val wordsFilterMediator: WordsFilterMediator = mock(WordsFilterMediator::class.java)
    val wordsRepository: WordsRepository = mock(WordsRepository::class.java)
    val filterParamsRepository: FilterParamsRepository = mock(FilterParamsRepository::class.java)
    val textToSpeechService: TextToSpeechService = mock(TextToSpeechService::class.java)
    val textToSpeechPreferencesRepository: TextToSpeechPreferencesRepository = mock(TextToSpeechPreferencesRepository::class.java)
    val wordUIMapper: ModelMapper<Word, WordUI> = mock(ModelMapper::class.java) as ModelMapper<Word, WordUI>
    val frequencyUIMapper: ModelMapper<Frequency, FrequencyUI> = mock(ModelMapper::class.java) as ModelMapper<Frequency, FrequencyUI>
    val partOfSpeechUIMapper: ModelMapper<PartOfSpeech, PartOfSpeechUI> = mock(ModelMapper::class.java) as ModelMapper<PartOfSpeech, PartOfSpeechUI>
    val wordUIResultMapper: ModelMapper<Result<Word>, UIState<WordUI>> = mock(ModelMapper::class.java) as ModelMapper<Result<Word>, UIState<WordUI>>
    val stringPageResultMapper: ModelMapper<Result<Page<String>>, UIState<Page<String>>> = mock(ModelMapper::class.java) as ModelMapper<Result<Page<String>>, UIState<Page<String>>>

    override fun onCreate() {
        super.onCreate()
        setTheme(R.style.AppTheme)
    }

    override fun getFacade(): ProvidersFacade {
        return facade
    }

    init {
        facade = object : ProvidersFacade {

            override fun provideContext(): Context {
                return context
            }

            override fun provideHomeMediator(): HomeMediator {
                return homeMediator
            }

            override fun provideDiscoverMediator(): DiscoverMediator {
                return discoverMediator
            }

            override fun provideSettingsMediator(): SettingsMediator {
                return settingsMediator
            }

            override fun provideWordMediator(): WordMediator {
                return wordMediator
            }

            override fun provideSearchWordMediator(): SearchWordMediator {
                return searchWordMediator
            }

            override fun provideWordsFilterMediator(): WordsFilterMediator {
                return wordsFilterMediator
            }

            override fun provideWordsRepository(): WordsRepository {
                return wordsRepository
            }

            override fun provideWordsFilterParamsRepository(): FilterParamsRepository {
                return filterParamsRepository
            }

            override fun provideTextToSpeechService(): TextToSpeechService {
                return textToSpeechService
            }

            override fun provideTextToSpeechPreferencesRepository(): TextToSpeechPreferencesRepository {
                return textToSpeechPreferencesRepository
            }

            override fun provideWordUIMapper(): ModelMapper<Word, WordUI> {
                return wordUIMapper
            }

            override fun provideFrequencyUIMapper(): ModelMapper<Frequency, FrequencyUI> {
                return frequencyUIMapper
            }

            override fun providePartOfSpeechUIMapper(): ModelMapper<PartOfSpeech, PartOfSpeechUI> {
                return partOfSpeechUIMapper
            }

            override fun provideWordUIResultMapper(): ModelMapper<Result<Word>, UIState<WordUI>> {
                return wordUIResultMapper
            }

            override fun provideStringPageResultMapper(): ModelMapper<Result<Page<String>>, UIState<Page<String>>> {
                return stringPageResultMapper
            }
        }
    }
}