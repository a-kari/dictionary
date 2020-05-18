package jp.neechan.akari.dictionary.common.db.dao

import androidx.paging.DataSource
import androidx.room.*
import jp.neechan.akari.dictionary.common.models.dto.DefinitionDto
import jp.neechan.akari.dictionary.common.models.dto.WordDto
import jp.neechan.akari.dictionary.common.models.dto.WordWithDefinitionsDto

@Dao
interface WordDao {

    @Query("SELECT word from Word ORDER BY saveDate DESC")
    fun getWords(): DataSource.Factory<Int, String>

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