package jp.neechan.akari.dictionary.home

import jp.neechan.akari.dictionary.common.db.DatabaseService
import jp.neechan.akari.dictionary.common.models.mappers.WordMapper
import jp.neechan.akari.dictionary.common.models.models.Word
import java.util.*

class WordsLocalRepository(private val databaseService: DatabaseService,
                           private val wordMapper: WordMapper) {

    suspend fun loadWords(): List<String> {
        return databaseService.getWordDao().getWords()
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
}