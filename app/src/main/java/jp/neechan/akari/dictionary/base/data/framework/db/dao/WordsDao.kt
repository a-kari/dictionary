package jp.neechan.akari.dictionary.base.data.framework.db.dao

import androidx.room.*
import jp.neechan.akari.dictionary.base.data.framework.dto.DefinitionDto
import jp.neechan.akari.dictionary.base.data.framework.dto.WordDto
import jp.neechan.akari.dictionary.base.data.framework.dto.WordWithDefinitionsDto
import jp.neechan.akari.dictionary.base.data.interface_adapters.WordsLocalSource

@Dao
interface WordsDao : WordsLocalSource {

    @Query("SELECT word FROM Word ORDER BY saveDate DESC LIMIT :limit OFFSET :offset")
    override suspend fun getWords(limit: Int, offset: Int): List<String>

    @Query("SELECT COUNT(1) FROM WORD")
    override suspend fun getWordsCount(): Int

    @Transaction
    @Query("SELECT * FROM Word WHERE word = :wordId LIMIT 1")
    override suspend fun getWord(wordId: String): WordWithDefinitionsDto?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    override suspend fun saveWord(word: WordDto)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    override suspend fun saveDefinitions(definitions: List<DefinitionDto>)

    @Query("DELETE FROM Word WHERE word = :wordId")
    override suspend fun deleteWord(wordId: String)
}