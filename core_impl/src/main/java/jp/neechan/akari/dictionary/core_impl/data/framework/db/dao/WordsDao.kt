package jp.neechan.akari.dictionary.core_impl.data.framework.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import jp.neechan.akari.dictionary.core_impl.data.framework.dto.DefinitionDto
import jp.neechan.akari.dictionary.core_impl.data.framework.dto.WordDto
import jp.neechan.akari.dictionary.core_impl.data.framework.dto.WordWithDefinitionsDto

@Dao
internal interface WordsDao {

    @Query("SELECT word FROM Word ORDER BY saveDate DESC LIMIT :limit OFFSET :offset")
    fun getWords(limit: Int, offset: Int): List<String>

    @Query("SELECT COUNT(1) FROM WORD")
    fun getWordsCount(): Int

    @Transaction
    @Query("SELECT * FROM Word WHERE word = :wordId LIMIT 1")
    suspend fun getWord(wordId: String): WordWithDefinitionsDto?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveWord(word: WordDto)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveDefinitions(definitions: List<DefinitionDto>)

    @Query("DELETE FROM Word WHERE word = :wordId")
    suspend fun deleteWord(wordId: String)
}