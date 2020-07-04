package jp.neechan.akari.dictionary.core_impl.data.framework.network

import dagger.Reusable
import jp.neechan.akari.dictionary.core_api.domain.entities.FilterParams
import jp.neechan.akari.dictionary.core_api.domain.entities.Page
import jp.neechan.akari.dictionary.core_api.domain.entities.Word
import jp.neechan.akari.dictionary.core_api.domain.entities.mappers.ModelMapper
import jp.neechan.akari.dictionary.core_impl.data.framework.dto.FilterParamsDto
import jp.neechan.akari.dictionary.core_impl.data.framework.dto.WordDto
import jp.neechan.akari.dictionary.core_impl.data.interface_adapters.WordsRemoteSource
import javax.inject.Inject

@Reusable
internal class WordsRemoteSourceImpl @Inject constructor(
    private val wordsApi: WordsApi,
    private val wordMapper: ModelMapper<Word, WordDto>,
    private val paramsMapper: ModelMapper<FilterParams, FilterParamsDto>
) : WordsRemoteSource {

    override suspend fun loadWords(params: FilterParams): Page<String> {
        val paramsDto = paramsMapper.mapToExternalLayer(params)
        return wordsApi.loadWords(paramsDto.toMap())
    }

    override suspend fun loadWord(wordId: String): Word {
        return wordMapper.mapToInternalLayer(wordsApi.loadWord(wordId))
    }
}
