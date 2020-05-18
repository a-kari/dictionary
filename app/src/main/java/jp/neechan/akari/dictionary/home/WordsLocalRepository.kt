package jp.neechan.akari.dictionary.home

import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import jp.neechan.akari.dictionary.common.db.DatabaseService
import jp.neechan.akari.dictionary.common.models.mappers.WordMapper
import jp.neechan.akari.dictionary.common.models.models.Word
import jp.neechan.akari.dictionary.discover.filter.WordsFilterParams
import java.util.*

class WordsLocalRepository(private val databaseService: DatabaseService,
                           private val wordMapper: WordMapper) {

    val wordsLiveData by lazy {
        val wordsLocalDataSourceFactory = databaseService.getWordDao().getWords()
        val config = PagedList.Config.Builder()
                              .setEnablePlaceholders(false)
                              .setPageSize(WordsFilterParams.DEFAULT_PAGE_SIZE)
                              .build()
        LivePagedListBuilder(wordsLocalDataSourceFactory, config).build()
    }

    suspend fun loadWord(wordId: String): Word? {
        val wordWithDefinitions = databaseService.getWordDao().getWord(wordId)

        return wordWithDefinitions?.let {
            val word = wordWithDefinitions.word
            word.definitions = wordWithDefinitions.definitions
            wordMapper.mapToHigherLayer(word)
        }
    }

    suspend fun saveWord(word: Word) {
        val wordDto = wordMapper.mapToLowerLayer(word)
        wordDto.saveDate = Date()

        databaseService.getWordDao().saveWord(wordDto)
        if (wordDto.definitions != null) {
            databaseService.getWordDao().saveDefinitions(wordDto.definitions!!)
        }
    }

    suspend fun deleteWord(word: String) {
        databaseService.getWordDao().deleteWord(word)
    }
}