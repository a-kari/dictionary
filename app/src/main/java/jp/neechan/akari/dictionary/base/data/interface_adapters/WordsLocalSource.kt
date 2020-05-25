package jp.neechan.akari.dictionary.base.data.interface_adapters

import jp.neechan.akari.dictionary.base.data.framework.dto.DefinitionDto
import jp.neechan.akari.dictionary.base.data.framework.dto.WordDto
import jp.neechan.akari.dictionary.base.data.framework.dto.WordWithDefinitionsDto

interface WordsLocalSource {

    suspend fun getWords(limit: Int, offset: Int): List<String>

    suspend fun getWordsCount(): Int

    suspend fun getWord(wordId: String): WordWithDefinitionsDto?

    suspend fun saveWord(word: WordDto)

    suspend fun saveDefinitions(definitions: List<DefinitionDto>)

    suspend fun deleteWord(wordId: String)
}