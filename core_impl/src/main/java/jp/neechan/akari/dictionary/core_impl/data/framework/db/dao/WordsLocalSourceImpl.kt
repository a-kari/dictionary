package jp.neechan.akari.dictionary.core_impl.data.framework.db.dao

import dagger.Reusable
import jp.neechan.akari.dictionary.core_api.domain.entities.FilterParams
import jp.neechan.akari.dictionary.core_api.domain.entities.Page
import jp.neechan.akari.dictionary.core_api.domain.entities.Word
import jp.neechan.akari.dictionary.core_api.domain.entities.mappers.ModelMapper
import jp.neechan.akari.dictionary.core_impl.data.framework.dto.WordDto
import jp.neechan.akari.dictionary.core_impl.data.interface_adapters.WordsLocalSource
import java.util.Date
import javax.inject.Inject

@Reusable
class WordsLocalSourceImpl @Inject constructor(
    private val wordsDao: WordsDao,
    private val wordMapper: ModelMapper<Word, WordDto>) : WordsLocalSource {

    override suspend fun loadWords(params: FilterParams): Page<String> {
        val page = params.page
        val limit = FilterParams.DEFAULT_PAGE_SIZE
        val offset = (page - 1) * limit
        val words = wordsDao.getWords(limit, offset)
        val total = wordsDao.getWordsCount()
        return Page(words, page, limit, total)
    }

    override suspend fun loadWord(wordId: String): Word? {
        val wordWithDefinitionsDto = wordsDao.getWordWithDefinitions(wordId)
        return wordWithDefinitionsDto?.let {
            wordWithDefinitionsDto.word.definitions = wordWithDefinitionsDto.definitions
            wordMapper.mapToInternalLayer(wordWithDefinitionsDto.word)
        }
    }

    override suspend fun saveWord(word: Word) {
        val wordDto = wordMapper.mapToExternalLayer(word)
        wordDto.saveDate = Date()
        wordsDao.saveWord(wordDto)

        if (wordDto.definitions != null) {
            wordsDao.saveDefinitions(wordDto.definitions!!)
        }
    }

    override suspend fun deleteWord(wordId: String) {
        wordsDao.deleteWord(wordId)
    }
}