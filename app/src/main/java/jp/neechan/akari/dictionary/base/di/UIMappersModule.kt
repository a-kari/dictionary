package jp.neechan.akari.dictionary.base.di

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.Reusable
import jp.neechan.akari.dictionary.base.domain.entities.Definition
import jp.neechan.akari.dictionary.base.domain.entities.Frequency
import jp.neechan.akari.dictionary.base.domain.entities.Page
import jp.neechan.akari.dictionary.base.domain.entities.PartOfSpeech
import jp.neechan.akari.dictionary.base.domain.entities.Result
import jp.neechan.akari.dictionary.base.domain.entities.Word
import jp.neechan.akari.dictionary.base.domain.entities.mappers.ModelMapper
import jp.neechan.akari.dictionary.base.presentation.models.DefinitionUI
import jp.neechan.akari.dictionary.base.presentation.models.FrequencyUI
import jp.neechan.akari.dictionary.base.presentation.models.PartOfSpeechUI
import jp.neechan.akari.dictionary.base.presentation.models.UIState
import jp.neechan.akari.dictionary.base.presentation.models.WordUI
import jp.neechan.akari.dictionary.base.presentation.models.mappers.DefinitionToDefinitionUIMapper
import jp.neechan.akari.dictionary.base.presentation.models.mappers.FrequencyToFrequencyUIMapper
import jp.neechan.akari.dictionary.base.presentation.models.mappers.PartOfSpeechToPartOfSpeechUIMapper
import jp.neechan.akari.dictionary.base.presentation.models.mappers.ResultToUIStateMapper
import jp.neechan.akari.dictionary.base.presentation.models.mappers.WordToWordUIMapper

@Module
abstract class UIMappersModule {

    @Module
    companion object {

        // Those mappers can't be just bound, because in the first case the mapper has no dependency
        // and in the second it has.
        @Provides
        @Reusable
        @JvmStatic
        fun provideStringPageResultMapper(): ModelMapper<Result<Page<String>>, UIState<Page<String>>> {
            return ResultToUIStateMapper()
        }

        @Provides
        @Reusable
        @JvmStatic
        fun provideWordUIResultMapper(wordMapper: WordToWordUIMapper): ModelMapper<Result<Word>, UIState<WordUI>> {
            return ResultToUIStateMapper(wordMapper)
        }
    }

    @Binds
    abstract fun bindWordMapper(
        mapper: WordToWordUIMapper
    ): ModelMapper<Word, WordUI>

    @Binds
    abstract fun bindDefinitionMapper(
        mapper: DefinitionToDefinitionUIMapper
    ): ModelMapper<Definition, DefinitionUI>

    @Binds
    abstract fun bindFrequencyMapper(
        mapper: FrequencyToFrequencyUIMapper
    ): ModelMapper<Frequency, FrequencyUI>

    @Binds
    abstract fun bindPartOfSpeechMapper(
        mapper: PartOfSpeechToPartOfSpeechUIMapper
    ): ModelMapper<PartOfSpeech, PartOfSpeechUI>
}