package jp.neechan.akari.dictionary.base.data.framework.network

import dagger.Reusable
import jp.neechan.akari.dictionary.base.data.framework.dto.FilterParamsDto
import jp.neechan.akari.dictionary.base.data.framework.dto.WordDto
import jp.neechan.akari.dictionary.base.data.interface_adapters.WordsRemoteSource
import jp.neechan.akari.dictionary.base.domain.entities.FilterParams
import jp.neechan.akari.dictionary.base.domain.entities.Page
import jp.neechan.akari.dictionary.base.domain.entities.Word
import jp.neechan.akari.dictionary.base.domain.entities.mappers.ModelMapper
import javax.inject.Inject

@Reusable
class WordsRemoteSourceImpl @Inject constructor(
    private val wordsApi: WordsApi,
    private val wordMapper: ModelMapper<Word, WordDto>,
    private val paramsMapper: ModelMapper<FilterParams, FilterParamsDto>) : WordsRemoteSource {

    override suspend fun loadWords(params: FilterParams): Page<String> {
        val paramsDto = paramsMapper.mapToExternalLayer(params)
        return wordsApi.loadWords(paramsDto.toMap())
    }

    override suspend fun loadWord(wordId: String): Word {
        return wordMapper.mapToInternalLayer(wordsApi.loadWord(wordId))
    }
}